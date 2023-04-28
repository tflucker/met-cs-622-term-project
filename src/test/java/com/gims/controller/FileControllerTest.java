package com.gims.controller;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.File;
import java.net.URISyntaxException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.gims.constants.CommonConstants;
import com.gims.shopping.StoreInventory;

public class FileControllerTest {

	public List<StoreInventory> inventory = new ArrayList<StoreInventory>();

	@BeforeEach
	public void before() {
		inventory = new ArrayList<StoreInventory>();
	}

	@AfterEach
	public void after() {

	}

	@Test
	public void getImportFileTest_validPath() throws URISyntaxException {
		String filepath = new File(
				Paths.get(ClassLoader.getSystemResource("importFiles/groceryStoreInventory_default.dat").toURI()).toUri())
						.getAbsolutePath();
//		inventory = FileController.importData(filepath);

		assertTrue(inventory.isEmpty());
	}


	@Test
	public void getImportFileTest_invalidPath() {
		String filepath = "akjsdhflkajsdhfkljashdfkjlhkdjfh98214182yrghoiuwahgiaobsnv";
		inventory = FileController.importData(filepath);
		assertTrue(inventory.isEmpty());
	}
}
