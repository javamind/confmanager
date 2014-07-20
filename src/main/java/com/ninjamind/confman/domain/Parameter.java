package com.ninjamind.confman.domain;

import com.google.common.base.Objects;

import javax.persistence.*;

/**
 * Each instances have paramaters
 *
 * @author EHRET_G
 */
@Entity
@Table(name=Parameter.TABLE_NAME)
public class Parameter extends AbstractConfManEntity<Parameter>{
    public final static String TABLE_NAME="parameter";
    public final static String SEQ_NAME= "seq_parameter";

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO, generator = Parameter.SEQ_NAME)
    @SequenceGenerator(name = Parameter.SEQ_NAME, sequenceName = Parameter.SEQ_NAME, allocationSize = 1)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "parameterGroupment_id")
    private ParameterGroupment parameterGroupment;
    @ManyToOne
    @JoinColumn(name = "instance_id")
    private Instance instance;

    public Parameter() {
    }

    public Instance getInstance() {
        return instance;
    }

    public Parameter setInstance(Instance instance) {
        this.instance = instance;
        return this;
    }

    public Long getId() {
        return id;
    }

    public Parameter setId(Long id) {
        this.id = id;
        return this;
    }

    public ParameterGroupment getParameterGroupment() {
        return parameterGroupment;
    }

    public Parameter setParameterGroupment(ParameterGroupment parameterGroupment) {
        this.parameterGroupment = parameterGroupment;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        Parameter parameter = (Parameter) o;

        if (instance != null ? !instance.equals(parameter.instance) : parameter.instance != null) return false;
        if (parameterGroupment != null ? !parameterGroupment.equals(parameter.parameterGroupment) : parameter.parameterGroupment != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (parameterGroupment != null ? parameterGroupment.hashCode() : 0);
        result = 31 * result + (instance != null ? instance.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return Objects.toStringHelper(this)
                .add("id", id)
                .addValue(super.toString())
                .add("parameterGroupment", parameterGroupment)
                .add("instance", instance)
                .toString();
    }
}