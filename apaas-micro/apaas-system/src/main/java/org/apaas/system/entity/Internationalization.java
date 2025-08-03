package org.apaas.system.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.SuperBuilder;
import org.apaas.core.domain.BaseEntity;
import org.springframework.data.relational.core.mapping.Table;

@Data
@SuperBuilder
@Table("internationalization")
@EqualsAndHashCode(callSuper = true)
public class Internationalization extends BaseEntity {
    private String code;
    private String message;
    private String language;
    private String country;
    private String region;
    private String locale;
    private String timeZone;
    private String currency;
    private String currencySymbol;
    private String currencyCode;
    private String currencySymbolPosition;
    private String currencyDecimalSeparator;
    private String currencyGroupingSeparator;
    private String currencyGroupingSize;
    private String currencyGroupingCount;
    private String currencyFormat;
    private String currencyFormatSymbols;

}
