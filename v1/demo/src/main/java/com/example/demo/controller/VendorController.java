package com.example.demo.controller;

import com.example.demo.entity.Vendors;
import com.example.demo.service.VendorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController // this will handle HTTP requests and return JSON responses.
@CrossOrigin(origins = "*") // Allows cross-origin requests from any domain (useful when the front-end is hosted on a different server).
public class VendorController {
    // This class will handle incoming HTTP requests related to Vendors.
    // You can define endpoints here to manage Vendors (like GET, POST, PUT, DELETE).

    @Autowired
    private VendorService vendorService;

    @GetMapping("/getallvendors")
    public List<Vendors>GetAllVendors(){
        return vendorService.getAllVendors();
    }

    @PostMapping("/createvendor")
    public ResponseEntity<Vendors>CreateNewStudent(@RequestBody Vendors vendors){
        Vendors createNewVendor = vendorService.createNewVendor(vendors);
        return ResponseEntity.status(201).body(createNewVendor);
    }

    @GetMapping("/getvendorbyid/{id}")
    public Vendors GetVendorByID(@PathVariable Long id){
        return vendorService.getVendorsByID(id);
    }

    @DeleteMapping("/deletevendors/{id}")
    public void DeleteVendorByID(@PathVariable Long id){
        vendorService.deleteVendorByID(id);
    }

    @PutMapping("/updatevendorbyid/{id}")
    public Vendors updateVendors(@PathVariable Long id , @RequestBody Vendors vendors){
        return vendorService.updateVendor(id, vendors);
    }

}
