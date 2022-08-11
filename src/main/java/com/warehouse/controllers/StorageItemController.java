package com.warehouse.controllers;

import com.warehouse.services.StorageItemService;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin("http://localhost")
public class StorageItemController {

	@GetMapping("/getStorageItems")
	public Object[] getStorageItems(){
		StorageItemService service = new StorageItemService();
		return service.getStorageItems().toArray();
	}
}
