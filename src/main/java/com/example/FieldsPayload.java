package com.example;

import java.util.Map;

public class FieldsPayload {
    private Map<String, String> fieldOptions;
    private Map<String, String> formulas;

    private Map<String, Double> userValues;

    public Map<String, String> getFieldOptions() {
        return fieldOptions;
    }

    public void setFieldOptions(Map<String, String> fieldOptions) {
        this.fieldOptions = fieldOptions;
    }

    public Map<String, String> getFormulas() {
        return formulas;
    }

    public void setFormulas(Map<String, String> formulas) {
        this.formulas = formulas;
    }

    public Map<String, Double> getUserValues() {
        return userValues;
    }

    public void setUserValues(Map<String, Double> userValues) {
        this.userValues = userValues;
    }
}
