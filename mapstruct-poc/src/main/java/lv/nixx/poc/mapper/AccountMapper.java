package lv.nixx.poc.mapper;

import lv.nixx.poc.model.dto.AccountDto;
import lv.nixx.poc.model.dto.TransactionDto;
import lv.nixx.poc.model.entity.AccountEntity;
import lv.nixx.poc.model.entity.TransactionEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.NullValueCheckStrategy;
import org.mapstruct.factory.Mappers;

import java.util.Collection;
import java.util.Date;

@Mapper(nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS)
public interface AccountMapper {

    AccountMapper INSTANCE = Mappers.getMapper(AccountMapper.class);

    @Mapping(target = "transactionCount", expression = "java(accountEntity.getTransactions().size())")
    @Mapping(target = "accountTypeMapped", source = "accountType", qualifiedByName = "accountTypeMapper", nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS)
    AccountDto accountToDto(AccountEntity accountEntity);

    Collection<TransactionDto> transactionToDto(Collection<TransactionEntity> c);

    @Mapping(target = "dateTime", source = "txnDateTime", dateFormat = "dd.MM.yyyy HH:mm:ss")
    TransactionDto transactionToDto(TransactionEntity c);

    default Long dateToLongMapper(Date date) {
        return date.getTime();
    }

    @Named("accountTypeMapper")
    default String accountTypeMapper(String accountType) {
        return accountType + ".mapped";
    }


}