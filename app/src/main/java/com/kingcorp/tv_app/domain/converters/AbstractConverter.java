package com.kingcorp.tv_app.domain.converters;

/**
 * Abstract class for converter class
 *
 * @param <Data> data-layer model class
 * @param <Domain> domain-layer model class
 */
public abstract class AbstractConverter<Data, Domain> {

    /**
     * Convert data-layer model to domain-layer model
     *
     * @param bean data-layer model
     * @return domain-layer model
     */
    public abstract Domain convert(Data bean);

    /**
     * Reverse domain-layer model to data-layer
     *
     * @param entity domain-layer model
     * @return data-layer model
     */
    public abstract Data reverse(Domain entity);

}