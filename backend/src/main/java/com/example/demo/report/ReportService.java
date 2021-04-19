package com.example.demo.report;

import javassist.bytecode.ByteArray;
import org.springframework.http.ResponseEntity;

public interface ReportService {

    byte[] export();

    ReportType getType();
}
