package com.example;

import net.objecthunter.exp4j.Expression;
import net.objecthunter.exp4j.ExpressionBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class FormulaController {

    @PostMapping("/fields")
    public ResponseEntity<Map<String, Double>> setFields(@RequestBody FieldsPayload payload) {
        Map<String, Double> results = new HashMap<>();
        Map<String, Boolean> calculated = new HashMap<>();

        // Initialize all fields to not calculated
        for (String field : payload.getFieldOptions().keySet()) {
            calculated.put(field, false);
        }

        // Calculate and store fields that are values, not formulas
        for (String field : payload.getFieldOptions().keySet()) {
            if ("value".equals(payload.getFieldOptions().get(field))) {
                results.put(field, payload.getUserValues().get(field));
                calculated.put(field, true);
            }
        }

        boolean progress;
        do {
            progress = false; // reset progress for each iteration

            for (String field : payload.getFieldOptions().keySet()) {
                if (!calculated.get(field) && "formula".equals(payload.getFieldOptions().get(field))) {
                    String formula = payload.getFormulas().get(field).replace("%", "/100*");
                    Expression e = new ExpressionBuilder(formula)
                            .variables(results.keySet()) // use results as variables
                            .build();

                    boolean allVarsCalculated = true;
                    for (String var : e.getVariableNames()) {
                        if (!calculated.get(var)) {
                            allVarsCalculated = false;
                            break;
                        }
                        e.setVariable(var, results.get(var));
                    }

                    if (allVarsCalculated) {
                        results.put(field, e.evaluate());
                        calculated.put(field, true);
                        progress = true; // made progress, so try for another pass
                    }
                }
            }
        } while (progress);

        // If there are any fields left uncalculated, it means there's a circular dependency
        for (String field : calculated.keySet()) {
            if (!calculated.get(field)) {
                throw new RuntimeException("Circular dependency detected. Unable to calculate the field: " + field);
            }
        }

        return ResponseEntity.ok(results);
    }
}

