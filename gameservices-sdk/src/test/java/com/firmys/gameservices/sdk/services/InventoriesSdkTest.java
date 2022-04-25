//package com.firmys.gameservices.sdk.services;
//
//import com.firmys.gameservices.models.Inventory;
//import org.junit.jupiter.api.BeforeAll;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.TestInstance;
//import org.mockito.Mockito;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.boot.test.mock.mockito.MockBean;
//import org.springframework.test.context.ActiveProfiles;
//import reactor.core.publisher.Mono;
//
//import java.util.Set;
//
//@SpringBootTest
//@ActiveProfiles("inventories-views-test")
//@TestInstance(TestInstance.Lifecycle.PER_CLASS)
//class InventoriesSdkTest {
//
//    @MockBean
//    InventoriesSdk inventoriesSdk;
//
//    @BeforeAll
//    void setup() {
//        Mockito.when(inventoriesSdk.addMultipleInventory(5))
//                .thenReturn(Mono.just(InventoryTestUtilities.generateInventories(5)));
//    }
//    @Test
//    void addMultipleInventory() {
//        Set<Inventory> inventories = inventoriesSdk.addMultipleInventory(5).block();
//        inventories.forEach(System.out::println);
//    }
//
//    @Test
//    void deleteMultipleInventory() {
//    }
//
//    @Test
//    void findMultipleInventory() {
//    }
//
//    @Test
//    void updateMultipleInventory() {
//    }
//}