package com.littlepage.drug_manage_system_android.api;

import com.littlepage.drug_manage_system_android.entity.Drug;

import org.junit.Test;


public class DrugTest {
    @Test
    public void testGetAllDrugs() {
        DrugApi.getAllDrugs().forEach(System.out::println);
    }
}
