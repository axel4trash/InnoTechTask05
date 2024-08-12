package edu.innotech.service.impl;

import edu.innotech.dto.*;
import edu.innotech.entity.Agreement;
import edu.innotech.entity.TppProduct;
import edu.innotech.entity.TppRefProductClass;
import edu.innotech.enums.EnumAccountTypes;
import edu.innotech.exceptions.BadRequestException;
import edu.innotech.exceptions.NoDataFoundException;
import edu.innotech.repository.AgreementRepository;
import edu.innotech.repository.TppProductRegisterRepository;
import edu.innotech.repository.TppProductRepository;
import edu.innotech.repository.TppRefProductClassRepository;
import edu.innotech.service.AccountService;
import edu.innotech.service.InstanceService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
public class InstanceServiceImpl implements InstanceService {

    @Autowired
    TppProductRepository tppProductRepository;

    @Autowired
    AgreementRepository agreementRepository;

    @Autowired
    TppRefProductClassRepository tppRefProductClassRepository;

    @Autowired
    AccountService accountService;

    @Override
    @Transactional
    public RespInstanceDto process(InstanceDto reqInstanceDto) {
        System.out.println(".process:");
        //instanceDto = reqInstanceDto;

        RespBodyInstanceDto respBodyInstanceDto = new RespBodyInstanceDto("", new ArrayList<>(), new ArrayList<>());

        System.out.println(".process: 1) reqInstanceDto: \n" + reqInstanceDto);
        System.out.println(" getNumber="+reqInstanceDto.getInstanceArrangement().get(0).getNumber());
        //Если ИД ЭП в поле Request.Body.instanceId не задано (NULL/Пусто), то выполняется процесс создания нового экземпляра
        if (reqInstanceDto.getInstanceId() == null) {
            createProduct(reqInstanceDto, respBodyInstanceDto);
        }else {
            respBodyInstanceDto.setInstanceId(reqInstanceDto.getInstanceId().toString());
        }
        System.out.println(".process: 2) InstanceId="+reqInstanceDto.getInstanceId());
        System.out.println(".process: respBodyInstanceDto: "+ respBodyInstanceDto);

        //создаётся ДопСоглашение к ЭП (договору) contractNumber
        createAgreement(reqInstanceDto, respBodyInstanceDto);

        RespInstanceDto respInstanceDto = new RespInstanceDto(respBodyInstanceDto);

        //4test
        /*if (respInstanceDto.getData().getInstanceId() == null){
             respInstanceDto.setData(new RespBodyInstanceDto("instanceId"
                    , Arrays.asList("registerId1", "registerIdN")
                    , Arrays.asList("supplementaryAgreementId1", "supplementaryAgreementIdN")
            ));
        }*/

        System.out.println(".process return: respInstanceDto: "+ respInstanceDto);
        return respInstanceDto;
    }

    void createProduct(InstanceDto reqInstanceDto, RespBodyInstanceDto respBodyInstanceDto){
        System.out.println("=========================");
        System.out.println(".createProduct: ");
        System.out.println("=========================");

        //Шаг 1.1 = Проверка таблицы ЭП (tpp_product) на дубли
        TppProduct tppProduct =  tppProductRepository.getFirstByNumber(reqInstanceDto.getContractNumber());

        if (tppProduct != null) {
            throw new BadRequestException("Параметр ContractNumber № договора " + reqInstanceDto.getContractNumber()
                    + " уже существует для ЭП с ИД  " + tppProduct.getId()+".");
        }

        //Шаг 1.2 = Проверка таблицы ДС (agreement) на дубли
        System.out.println("Шаг 1.2 = Проверка таблицы ДС (agreement) на дубли");
        reqInstanceDto.getInstanceArrangement().forEach(sa -> {
            System.out.println(sa.getNumber());
            Agreement agreement = agreementRepository.getFirstByNumber(sa.getNumber());
            if(agreement != null){
                throw new BadRequestException("Параметр № Дополнительного соглашения (сделки) Number "
                                                + sa.getNumber()+" уже существует для ЭП с ИД "
                                                + agreement.getId());
            }
        }
        );

        //Шаг 1.3 По КодуПродукта (ProductCode) найти связные записи в Каталоге продуктов
        List<TppRefProductClass> tppRefProductClasses = tppRefProductClassRepository.getListProductCatalog(reqInstanceDto.getProductCode(), String.valueOf(EnumAccountTypes.CL));
        if (tppRefProductClasses.isEmpty()) {
            throw new NoDataFoundException("Продукта с кодом " + reqInstanceDto.getProductCode() + " не найден!");
        }

        List<String> registryIdList = new ArrayList<>();
        tppRefProductClasses.forEach(it -> registryIdList.add(it.getInternal_id().toString()));
        respBodyInstanceDto.setRegisterId(registryIdList);

        //Шаг 1.4 = Добавить строки в таблицу tpp_product
        TppProduct newTppProduct = TppProduct.builder()
                .product_code_id(Long.valueOf(registryIdList.get(0)))
                .number(reqInstanceDto.getContractNumber())
                .client_id(Long.valueOf(reqInstanceDto.getMdmCode()))
                .type(reqInstanceDto.getProductType())
                .priority(reqInstanceDto.getPriority().longValue())
                .date_of_conclusion(new Timestamp(reqInstanceDto.getContractDate().getTime()))
                .interest_rate_type(reqInstanceDto.getRateType())
                .penalty_rate(reqInstanceDto.getInterestRatePenalty())
                .nso(reqInstanceDto.getMinimalBalance())
                .threshold_amount(reqInstanceDto.getThresholdAmount())
                .tax_rate(reqInstanceDto.getTaxPercentageRate())
                .build();
        tppProductRepository.save(newTppProduct);
        System.out.println("after SAVE: newTppProduct=" + newTppProduct);
        respBodyInstanceDto.setInstanceId(newTppProduct.getId().toString());

        //Шаг 1.5 = Добавить в таблицу ПР (tpp_product_registry) строки
        RespAccountDto respAccountDto = accountService.createProductRegister(
                AccountDto.builder()
                        .instanceId(Long.valueOf(newTppProduct.getId()))
                        .registryTypeCode(reqInstanceDto.getRegisterType())
                        .accountType(String.valueOf(EnumAccountTypes.CL))
                        .currencyCode(reqInstanceDto.getIsoCurrencyCode())
                        .branchCode(reqInstanceDto.getBranchCode())
                        .priorityCode(reqInstanceDto.getUrgencyCode())
                        .mdmCode(reqInstanceDto.getMdmCode())
                        .build()
        );
    }


    void createAgreement(InstanceDto reqInstanceDto, RespBodyInstanceDto respBodyInstanceDto){
        System.out.println("=========================");
        System.out.println(".createAgreement: by InstanceId="+respBodyInstanceDto.getInstanceId());
        System.out.println("=========================");
        //Шаг 2.1 Проверка таблицы ЭП (tpp_product) на существование ЭП
        TppProduct tppProduct = tppProductRepository.findById(Long.valueOf(respBodyInstanceDto.getInstanceId()))
                            .orElseThrow(()-> new NoDataFoundException("Экземпляр продукта с параметром instanceId "+reqInstanceDto.getInstanceId()+" не найден."));

        //Шаг 2.2 Проверка таблицы ДС (agreement) на дубли
        System.out.println("Шаг 2.2 = Проверка таблицы ДС (agreement) на дубли");
        List<AgreementDto> agreementDtoList = reqInstanceDto.getInstanceArrangement();
        agreementDtoList.forEach(sa -> {
                    System.out.println(sa.getNumber());
                    Agreement agreement = agreementRepository.getFirstByNumber(sa.getNumber());
                    if(agreement != null){
                        throw new BadRequestException("Параметр № Дополнительного соглашения (сделки) Number "
                                + sa.getNumber()+" уже существует для ЭП с ИД "
                                + agreement.getId());
                    }
                }
        );

        //Шаг 2.3 Добавить строку в таблицу ДС (agreement)
        List<String> agreementIdList = new ArrayList<>();
        agreementDtoList.forEach(ag->
                {Agreement newAgreement = Agreement.builder()
                        .product_id(tppProduct)
                        .number(ag.getNumber())
                        .opening_date(ag.getOpeningDate())

                        .general_agreement_id(ag.getGeneralAgreementId())
                        .supplementary_agreement_id(ag.getSupplementaryAgreementId())
                        .arrangement_type(ag.getArrangementType())
                        .scheduler_job_id(ag.getSchedulerJobId())
                        .closing_date(ag.getClosingDate())
                        .cancel_date(ag.getCancelDate())
                        .validity_duration(ag.getValidityDuration())
                        .cancellation_reason(ag.getCancellationReason())
                        .status(ag.getStatus())
                        .interest_rate(ag.getInterestRate())
                        .interest_calculation_date(ag.getInterestCalculationDate())
                        .coefficient(ag.getCoefficient())
                        .coefficient_action(ag.getCoefficientAction())
                        .minimum_interest_rate(ag.getMinInterestRate())
                        .minimum_interest_rate_coefficient(ag.getMinInterestRateCoefficient())
                        .minimum_interest_rate_coefficient_action(ag.getMinInterestRateCoefficientAction())
                        .maximal_interest_rate(ag.getMaxInterestRate())
                        .maximal_interest_rate_coefficient(ag.getMaxInterestRateCoefficient())
                        .maximal_interest_rate_coefficient_action(ag.getMaxInterestRateCoefficientAction())
                        .build();

                    agreementRepository.save(newAgreement);
                    agreementIdList.add(newAgreement.getId().toString());
                }
                );

        respBodyInstanceDto.setSupplementaryAgreementId(agreementIdList);

        if (respBodyInstanceDto.getInstanceId() == null){
            respBodyInstanceDto.setInstanceId(tppProduct.getId().toString());
            List<TppRefProductClass> tppRefProductClasses = tppRefProductClassRepository.getListProductCatalog(reqInstanceDto.getProductCode(), String.valueOf(EnumAccountTypes.CL));
            if (tppRefProductClasses.isEmpty()) {
                throw new NoDataFoundException("Продукта с кодом " + reqInstanceDto.getProductCode() + " не найден!");
            }
            List<String> registryIdList = new ArrayList<>();
            tppRefProductClasses.forEach(it -> registryIdList.add(it.getInternal_id().toString()));
            respBodyInstanceDto.setRegisterId(registryIdList);
        }
    }
}
