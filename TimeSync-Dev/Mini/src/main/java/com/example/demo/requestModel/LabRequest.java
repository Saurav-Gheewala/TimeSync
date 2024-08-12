package com.example.demo.requestModel;

import com.example.demo.model.LabInput;
import lombok.Data;

import java.util.Set;
@Data
public class LabRequest {
    private Set<LabInput> name;
}
