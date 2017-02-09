databaseChangeLog = {

	changeSet(author: "rcancino (generated)", id: "1438439387978-1") {
		createTable(tableName: "abono") {
			column(autoIncrement: "true", name: "id", type: "BIGINT") {
				constraints(nullable: "false", primaryKey: "true")
			}

			column(name: "version", type: "BIGINT") {
				constraints(nullable: "false")
			}

			column(name: "comentario", type: "VARCHAR(200)")

			column(name: "date_created", type: "DATETIME") {
				constraints(nullable: "false")
			}

			column(name: "fecha", type: "DATETIME") {
				constraints(nullable: "false")
			}

			column(name: "importe", type: "DECIMAL(19,2)") {
				constraints(nullable: "false")
			}

			column(name: "impuesto_tasa", type: "DECIMAL(19,2)") {
				constraints(nullable: "false")
			}

			column(name: "impuestos", type: "DECIMAL(19,2)") {
				constraints(nullable: "false")
			}

			column(name: "last_updated", type: "DATETIME") {
				constraints(nullable: "false")
			}

			column(name: "moneda", type: "VARCHAR(255)") {
				constraints(nullable: "false")
			}

			column(name: "proveedor_id", type: "BIGINT") {
				constraints(nullable: "false")
			}

			column(name: "tc", type: "DECIMAL(19,4)") {
				constraints(nullable: "false")
			}

			column(name: "total", type: "DECIMAL(19,2)") {
				constraints(nullable: "false")
			}

			column(name: "class", type: "VARCHAR(255)") {
				constraints(nullable: "false")
			}

			column(name: "concepto", type: "VARCHAR(20)")

			column(name: "documento", type: "VARCHAR(20)")

			column(name: "pago_proveedor_id", type: "BIGINT")
		}
	}

	changeSet(author: "rcancino (generated)", id: "1438439387978-2") {
		createTable(tableName: "aduana") {
			column(autoIncrement: "true", name: "id", type: "BIGINT") {
				constraints(nullable: "false", primaryKey: "true")
			}

			column(name: "version", type: "BIGINT") {
				constraints(nullable: "false")
			}

			column(name: "direccion_calle", type: "VARCHAR(200)") {
				constraints(nullable: "false")
			}

			column(name: "direccion_codigo_postal", type: "VARCHAR(255)") {
				constraints(nullable: "false")
			}

			column(name: "direccion_colonia", type: "VARCHAR(255)")

			column(name: "direccion_estado", type: "VARCHAR(255)") {
				constraints(nullable: "false")
			}

			column(name: "direccion_municipio", type: "VARCHAR(255)")

			column(name: "direccion_numero_exterior", type: "VARCHAR(50)")

			column(name: "direccion_numero_interior", type: "VARCHAR(50)")

			column(name: "direccion_pais", type: "VARCHAR(100)") {
				constraints(nullable: "false")
			}

			column(name: "nombre", type: "VARCHAR(50)") {
				constraints(nullable: "false")
			}
		}
	}

	changeSet(author: "rcancino (generated)", id: "1438439387978-3") {
		createTable(tableName: "anticipo") {
			column(autoIncrement: "true", name: "id", type: "BIGINT") {
				constraints(nullable: "false", primaryKey: "true")
			}

			column(name: "version", type: "BIGINT") {
				constraints(nullable: "false")
			}

			column(name: "complemento_id", type: "BIGINT")

			column(name: "date_created", type: "DATETIME") {
				constraints(nullable: "false")
			}

			column(name: "fecha", type: "DATETIME") {
				constraints(nullable: "false")
			}

			column(name: "gastos_de_importacion", type: "DECIMAL(19,2)") {
				constraints(nullable: "false")
			}

			column(name: "impuestos_aduanales", type: "DECIMAL(19,2)") {
				constraints(nullable: "false")
			}

			column(name: "last_updated", type: "DATETIME") {
				constraints(nullable: "false")
			}

			column(name: "requisicion_id", type: "BIGINT") {
				constraints(nullable: "false")
			}

			column(name: "sobrante_id", type: "BIGINT")

			column(name: "total", type: "DECIMAL(19,2)") {
				constraints(nullable: "false")
			}
		}
	}

	changeSet(author: "rcancino (generated)", id: "1438439387978-4") {
		createTable(tableName: "aplicacion") {
			column(autoIncrement: "true", name: "id", type: "BIGINT") {
				constraints(nullable: "false", primaryKey: "true")
			}

			column(name: "version", type: "BIGINT") {
				constraints(nullable: "false")
			}

			column(name: "abono_id", type: "BIGINT") {
				constraints(nullable: "false")
			}

			column(name: "comentario", type: "VARCHAR(200)")

			column(name: "date_created", type: "DATETIME") {
				constraints(nullable: "false")
			}

			column(name: "fecha", type: "DATETIME") {
				constraints(nullable: "false")
			}

			column(name: "importe", type: "DECIMAL(19,2)") {
				constraints(nullable: "false")
			}

			column(name: "impuesto", type: "DECIMAL(19,2)") {
				constraints(nullable: "false")
			}

			column(name: "impuesto_tasa", type: "DECIMAL(19,2)") {
				constraints(nullable: "false")
			}

			column(name: "last_updated", type: "DATETIME") {
				constraints(nullable: "false")
			}

			column(name: "total", type: "DECIMAL(19,2)") {
				constraints(nullable: "false")
			}

			column(name: "aplicaciones_idx", type: "INT")

			column(name: "factura_id", type: "BIGINT") {
				constraints(nullable: "false")
			}
		}
	}

	changeSet(author: "rcancino (generated)", id: "1438439387978-5") {
		createTable(tableName: "banco") {
			column(autoIncrement: "true", name: "id", type: "BIGINT") {
				constraints(nullable: "false", primaryKey: "true")
			}

			column(name: "version", type: "BIGINT") {
				constraints(nullable: "false")
			}

			column(name: "nombre", type: "VARCHAR(150)") {
				constraints(nullable: "false")
			}
		}
	}

	changeSet(author: "rcancino (generated)", id: "1438439387978-6") {
		createTable(tableName: "cancelacion_de_cargo") {
			column(autoIncrement: "true", name: "id", type: "BIGINT") {
				constraints(nullable: "false", primaryKey: "true")
			}

			column(name: "version", type: "BIGINT") {
				constraints(nullable: "false")
			}

			column(name: "cargo_id", type: "BIGINT") {
				constraints(nullable: "false")
			}

			column(name: "comentario", type: "LONGTEXT") {
				constraints(nullable: "false")
			}

			column(name: "date_created", type: "DATETIME") {
				constraints(nullable: "false")
			}

			column(name: "fecha", type: "DATETIME") {
				constraints(nullable: "false")
			}

			column(name: "usuario", type: "VARCHAR(255)") {
				constraints(nullable: "false")
			}
		}
	}

	changeSet(author: "rcancino (generated)", id: "1438439387978-7") {
		createTable(tableName: "certificado") {
			column(autoIncrement: "true", name: "id", type: "BIGINT") {
				constraints(nullable: "false", primaryKey: "true")
			}

			column(name: "version", type: "BIGINT") {
				constraints(nullable: "false")
			}

			column(name: "algoritmo", type: "VARCHAR(40)") {
				constraints(nullable: "false")
			}

			column(name: "certificado_path", type: "VARCHAR(255)") {
				constraints(nullable: "false")
			}

			column(name: "expedicion", type: "DATETIME") {
				constraints(nullable: "false")
			}

			column(name: "numero_de_certificado", type: "VARCHAR(255)") {
				constraints(nullable: "false")
			}

			column(name: "private_key_path", type: "VARCHAR(255)") {
				constraints(nullable: "false")
			}

			column(name: "vencimiento", type: "DATETIME") {
				constraints(nullable: "false")
			}
		}
	}

	changeSet(author: "rcancino (generated)", id: "1438439387978-8") {
		createTable(tableName: "cfdi") {
			column(autoIncrement: "true", name: "id", type: "BIGINT") {
				constraints(nullable: "false", primaryKey: "true")
			}

			column(name: "version", type: "BIGINT") {
				constraints(nullable: "false")
			}

			column(name: "cadena_original", type: "LONGTEXT")

			column(name: "comentario", type: "LONGTEXT")

			column(name: "date_created", type: "DATETIME") {
				constraints(nullable: "false")
			}

			column(name: "descuentos", type: "DECIMAL(19,2)") {
				constraints(nullable: "false")
			}

			column(name: "emisor", type: "LONGTEXT") {
				constraints(nullable: "false")
			}

			column(name: "fecha", type: "DATETIME") {
				constraints(nullable: "false")
			}

			column(name: "folio", type: "VARCHAR(20)") {
				constraints(nullable: "false")
			}

			column(name: "importe", type: "DECIMAL(19,2)") {
				constraints(nullable: "false")
			}

			column(name: "impuesto", type: "DECIMAL(19,2)") {
				constraints(nullable: "false")
			}

			column(name: "last_updated", type: "DATETIME") {
				constraints(nullable: "false")
			}

			column(name: "origen", type: "VARCHAR(255)") {
				constraints(nullable: "false")
			}

			column(name: "receptor", type: "LONGTEXT") {
				constraints(nullable: "false")
			}

			column(name: "rfc", type: "VARCHAR(13)") {
				constraints(nullable: "false")
			}

			column(name: "serie", type: "VARCHAR(15)") {
				constraints(nullable: "false")
			}

			column(name: "subtotal", type: "DECIMAL(19,2)") {
				constraints(nullable: "false")
			}

			column(name: "timbrado", type: "DATETIME")

			column(name: "tipo", type: "VARCHAR(12)") {
				constraints(nullable: "false")
			}

			column(name: "tipo_de_cfdi", type: "VARCHAR(1)") {
				constraints(nullable: "false")
			}

			column(name: "total", type: "DECIMAL(19,2)") {
				constraints(nullable: "false")
			}

			column(name: "url", type: "VARCHAR(255)")

			column(name: "uuid", type: "LONGTEXT")

			column(name: "xml", type: "MEDIUMBLOB") {
				constraints(nullable: "false")
			}

			column(name: "xml_name", type: "VARCHAR(200)")
		}
	}

	changeSet(author: "rcancino (generated)", id: "1438439387978-9") {
		createTable(tableName: "cfdi_folio") {
			column(autoIncrement: "true", name: "id", type: "BIGINT") {
				constraints(nullable: "false", primaryKey: "true")
			}

			column(name: "version", type: "BIGINT") {
				constraints(nullable: "false")
			}

			column(name: "emisor", type: "VARCHAR(20)") {
				constraints(nullable: "false")
			}

			column(name: "folio", type: "BIGINT") {
				constraints(nullable: "false")
			}

			column(name: "serie", type: "VARCHAR(10)") {
				constraints(nullable: "false")
			}
		}
	}

	changeSet(author: "rcancino (generated)", id: "1438439387978-10") {
		createTable(tableName: "cheque") {
			column(autoIncrement: "true", name: "id", type: "BIGINT") {
				constraints(nullable: "false", primaryKey: "true")
			}

			column(name: "version", type: "BIGINT") {
				constraints(nullable: "false")
			}

			column(name: "cuenta_id", type: "BIGINT") {
				constraints(nullable: "false")
			}

			column(name: "egreso_id", type: "BIGINT") {
				constraints(nullable: "false")
			}

			column(name: "fecha_impresion", type: "DATETIME")

			column(name: "folio", type: "INT") {
				constraints(nullable: "false")
			}

			column(name: "cancelacion", type: "DATETIME")

			column(name: "comentario_cancelacion", type: "VARCHAR(255)")
		}
	}

	changeSet(author: "rcancino (generated)", id: "1438439387978-11") {
		createTable(tableName: "clase") {
			column(autoIncrement: "true", name: "id", type: "BIGINT") {
				constraints(nullable: "false", primaryKey: "true")
			}

			column(name: "version", type: "BIGINT") {
				constraints(nullable: "false")
			}

			column(name: "date_created", type: "DATETIME") {
				constraints(nullable: "false")
			}

			column(name: "last_updated", type: "DATETIME") {
				constraints(nullable: "false")
			}

			column(name: "nombre", type: "VARCHAR(20)") {
				constraints(nullable: "false")
			}
		}
	}

	changeSet(author: "rcancino (generated)", id: "1438439387978-12") {
		createTable(tableName: "cliente") {
			column(autoIncrement: "true", name: "id", type: "BIGINT") {
				constraints(nullable: "false", primaryKey: "true")
			}

			column(name: "version", type: "BIGINT") {
				constraints(nullable: "false")
			}

			column(name: "cuenta_de_pago", type: "VARCHAR(4)")

			column(name: "date_created", type: "DATETIME") {
				constraints(nullable: "false")
			}

			column(name: "direccion_calle", type: "VARCHAR(200)")

			column(name: "direccion_codigo_postal", type: "VARCHAR(255)")

			column(name: "direccion_colonia", type: "VARCHAR(255)")

			column(name: "direccion_estado", type: "VARCHAR(255)")

			column(name: "direccion_municipio", type: "VARCHAR(255)")

			column(name: "direccion_numero_exterior", type: "VARCHAR(50)")

			column(name: "direccion_numero_interior", type: "VARCHAR(50)")

			column(name: "direccion_pais", type: "VARCHAR(100)")

			column(name: "email1", type: "VARCHAR(255)")

			column(name: "fisica", type: "BIT") {
				constraints(nullable: "false")
			}

			column(name: "forma_de_pago", type: "VARCHAR(255)")

			column(name: "last_updated", type: "DATETIME") {
				constraints(nullable: "false")
			}

			column(name: "nombre", type: "VARCHAR(255)") {
				constraints(nullable: "false")
			}

			column(name: "rfc", type: "VARCHAR(255)")

			column(name: "sub_cuenta_operativa", type: "VARCHAR(4)")
		}
	}

	changeSet(author: "rcancino (generated)", id: "1438439387978-13") {
		createTable(tableName: "comision") {
			column(autoIncrement: "true", name: "id", type: "BIGINT") {
				constraints(nullable: "false", primaryKey: "true")
			}

			column(name: "version", type: "BIGINT") {
				constraints(nullable: "false")
			}

			column(name: "comentario", type: "VARCHAR(200)")

			column(name: "comision", type: "DECIMAL(19,2)") {
				constraints(nullable: "false")
			}

			column(name: "cuenta_id", type: "BIGINT") {
				constraints(nullable: "false")
			}

			column(name: "fecha", type: "DATETIME") {
				constraints(nullable: "false")
			}

			column(name: "impuesto", type: "DECIMAL(19,2)") {
				constraints(nullable: "false")
			}

			column(name: "impuesto_tasa", type: "DECIMAL(19,2)") {
				constraints(nullable: "false")
			}

			column(name: "referencia_bancaria", type: "VARCHAR(100)")

			column(name: "tc", type: "DECIMAL(19,4)") {
				constraints(nullable: "false")
			}
		}
	}

	changeSet(author: "rcancino (generated)", id: "1438439387978-14") {
		createTable(tableName: "comision_movimiento_de_cuenta") {
			column(name: "comision_movimientos_id", type: "BIGINT")

			column(name: "movimiento_de_cuenta_id", type: "BIGINT")
		}
	}

	changeSet(author: "rcancino (generated)", id: "1438439387978-15") {
		createTable(tableName: "compra") {
			column(autoIncrement: "true", name: "id", type: "BIGINT") {
				constraints(nullable: "false", primaryKey: "true")
			}

			column(name: "version", type: "BIGINT") {
				constraints(nullable: "false")
			}

			column(name: "comentario", type: "VARCHAR(255)") {
				constraints(nullable: "false")
			}

			column(name: "date_created", type: "DATETIME") {
				constraints(nullable: "false")
			}

			column(name: "depuracion", type: "DATETIME")

			column(name: "descuentos", type: "DECIMAL(19,2)") {
				constraints(nullable: "false")
			}

			column(name: "entrega", type: "DATETIME")

			column(name: "fecha", type: "DATETIME") {
				constraints(nullable: "false")
			}

			column(name: "folio", type: "VARCHAR(255)")

			column(name: "importe", type: "DECIMAL(19,2)") {
				constraints(nullable: "false")
			}

			column(name: "impuestos", type: "DECIMAL(19,2)") {
				constraints(nullable: "false")
			}

			column(name: "last_updated", type: "DATETIME") {
				constraints(nullable: "false")
			}

			column(name: "moneda", type: "VARCHAR(255)") {
				constraints(nullable: "false")
			}

			column(name: "origen", type: "VARCHAR(255)")

			column(name: "proveedor_id", type: "BIGINT") {
				constraints(nullable: "false")
			}

			column(name: "subtotal", type: "DECIMAL(19,2)") {
				constraints(nullable: "false")
			}

			column(name: "tc", type: "DECIMAL(19,4)") {
				constraints(nullable: "false")
			}

			column(name: "total", type: "DECIMAL(19,2)") {
				constraints(nullable: "false")
			}
		}
	}

	changeSet(author: "rcancino (generated)", id: "1438439387978-16") {
		createTable(tableName: "compra_de_moneda") {
			column(autoIncrement: "true", name: "id", type: "BIGINT") {
				constraints(nullable: "false", primaryKey: "true")
			}

			column(name: "version", type: "BIGINT") {
				constraints(nullable: "false")
			}

			column(name: "cuenta_destino_id", type: "BIGINT") {
				constraints(nullable: "false")
			}

			column(name: "cuenta_origen_id", type: "BIGINT") {
				constraints(nullable: "false")
			}

			column(name: "date_created", type: "DATETIME") {
				constraints(nullable: "false")
			}

			column(name: "diferencia_cambiaria", type: "DECIMAL(19,2)") {
				constraints(nullable: "false")
			}

			column(name: "fecha", type: "DATETIME") {
				constraints(nullable: "false")
			}

			column(name: "forma_de_pago", type: "VARCHAR(255)") {
				constraints(nullable: "false")
			}

			column(name: "last_updated", type: "DATETIME") {
				constraints(nullable: "false")
			}

			column(name: "moneda", type: "VARCHAR(255)") {
				constraints(nullable: "false")
			}

			column(name: "pago_proveedor_id", type: "BIGINT") {
				constraints(nullable: "false")
			}

			column(name: "requisicion_id", type: "BIGINT") {
				constraints(nullable: "false")
			}

			column(name: "tipo_de_cambio", type: "DECIMAL(19,6)") {
				constraints(nullable: "false")
			}

			column(name: "tipo_de_cambio_compra", type: "DECIMAL(19,6)") {
				constraints(nullable: "false")
			}

			column(name: "ingreso_id", type: "BIGINT") {
				constraints(nullable: "false")
			}
		}
	}

	changeSet(author: "rcancino (generated)", id: "1438439387978-17") {
		createTable(tableName: "compra_det") {
			column(autoIncrement: "true", name: "id", type: "BIGINT") {
				constraints(nullable: "false", primaryKey: "true")
			}

			column(name: "version", type: "BIGINT") {
				constraints(nullable: "false")
			}

			column(name: "compra_id", type: "BIGINT") {
				constraints(nullable: "false")
			}

			column(name: "descuento", type: "DECIMAL(19,2)")

			column(name: "entregado", type: "DECIMAL(19,2)") {
				constraints(nullable: "false")
			}

			column(name: "importe", type: "DECIMAL(19,2)") {
				constraints(nullable: "false")
			}

			column(name: "importe_descuento", type: "DECIMAL(19,2)") {
				constraints(nullable: "false")
			}

			column(name: "precio", type: "DECIMAL(19,2)") {
				constraints(nullable: "false")
			}

			column(name: "producto_id", type: "BIGINT") {
				constraints(nullable: "false")
			}

			column(name: "solicitado", type: "DECIMAL(19,2)") {
				constraints(nullable: "false")
			}

			column(name: "partidas_idx", type: "INT")
		}
	}

	changeSet(author: "rcancino (generated)", id: "1438439387978-18") {
		createTable(tableName: "comprobante_fiscal") {
			column(autoIncrement: "true", name: "id", type: "BIGINT") {
				constraints(nullable: "false", primaryKey: "true")
			}

			column(name: "version", type: "BIGINT") {
				constraints(nullable: "false")
			}

			column(name: "comentario", type: "VARCHAR(255)")

			column(name: "folio", type: "BIGINT") {
				constraints(nullable: "false")
			}

			column(name: "numero_de_certificado", type: "VARCHAR(255)") {
				constraints(nullable: "false")
			}

			column(name: "origen", type: "BIGINT") {
				constraints(nullable: "false")
			}

			column(name: "tipo", type: "VARCHAR(15)") {
				constraints(nullable: "false")
			}

			column(name: "xml_path", type: "VARCHAR(255)") {
				constraints(nullable: "false")
			}

			column(name: "ano_aprobacion", type: "INT")

			column(name: "estado", type: "VARCHAR(1)")

			column(name: "fecha", type: "DATETIME")

			column(name: "impuesto", type: "DECIMAL(19,2)")

			column(name: "numero_de_aprobacion", type: "INT")

			column(name: "rfc", type: "VARCHAR(13)")

			column(name: "serie", type: "VARCHAR(50)")

			column(name: "tipo_cfd", type: "VARCHAR(1)")

			column(name: "total", type: "DECIMAL(19,2)")

			column(name: "aduana", type: "VARCHAR(255)")

			column(name: "fecha_pedimento", type: "DATETIME")

			column(name: "pedimento", type: "VARCHAR(255)")
		}
	}

	changeSet(author: "rcancino (generated)", id: "1438439387978-19") {
		createTable(tableName: "concepto_de_gasto") {
			column(autoIncrement: "true", name: "id", type: "BIGINT") {
				constraints(nullable: "false", primaryKey: "true")
			}

			column(name: "version", type: "BIGINT") {
				constraints(nullable: "false")
			}

			column(name: "concepto_id", type: "BIGINT") {
				constraints(nullable: "false")
			}

			column(name: "descripcion", type: "LONGTEXT") {
				constraints(nullable: "false")
			}

			column(name: "egreso_id", type: "BIGINT")

			column(name: "factura_id", type: "BIGINT") {
				constraints(nullable: "false")
			}

			column(name: "ietu", type: "DECIMAL(19,2)") {
				constraints(nullable: "false")
			}

			column(name: "importe", type: "DECIMAL(19,2)") {
				constraints(nullable: "false")
			}

			column(name: "impuesto", type: "DECIMAL(19,2)") {
				constraints(nullable: "false")
			}

			column(name: "impuesto_tasa", type: "DECIMAL(19,2)") {
				constraints(nullable: "false")
			}

			column(name: "retension", type: "DECIMAL(19,2)") {
				constraints(nullable: "false")
			}

			column(name: "retension_isr", type: "DECIMAL(19,2)") {
				constraints(nullable: "false")
			}

			column(name: "retension_isr_tasa", type: "DECIMAL(19,2)") {
				constraints(nullable: "false")
			}

			column(name: "retension_tasa", type: "DECIMAL(19,2)") {
				constraints(nullable: "false")
			}

			column(name: "total", type: "DECIMAL(19,2)") {
				constraints(nullable: "false")
			}

			column(name: "tipo", type: "VARCHAR(100)")

			column(name: "comentario_otros", type: "VARCHAR(255)")

			column(name: "descuento", type: "DECIMAL(19,2)")

			column(name: "fecha_rembolso", type: "DATETIME")

			column(name: "otros", type: "DECIMAL(19,2)")

			column(name: "rembolso", type: "DECIMAL(19,2)")
		}
	}

	changeSet(author: "rcancino (generated)", id: "1438439387978-20") {
		createTable(tableName: "cuenta_bancaria") {
			column(autoIncrement: "true", name: "id", type: "BIGINT") {
				constraints(nullable: "false", primaryKey: "true")
			}

			column(name: "version", type: "BIGINT") {
				constraints(nullable: "false")
			}

			column(name: "activo", type: "BIT") {
				constraints(nullable: "false")
			}

			column(name: "banco_id", type: "BIGINT") {
				constraints(nullable: "false")
			}

			column(name: "moneda", type: "VARCHAR(255)") {
				constraints(nullable: "false")
			}

			column(name: "nombre", type: "VARCHAR(100)") {
				constraints(nullable: "false")
			}

			column(name: "numero", type: "VARCHAR(50)") {
				constraints(nullable: "false")
			}

			column(name: "tipo", type: "VARCHAR(50)") {
				constraints(nullable: "false")
			}

			column(name: "folio", type: "INT") {
				constraints(nullable: "false")
			}

			column(name: "folio_final", type: "INT") {
				constraints(nullable: "false")
			}

			column(name: "folio_inicial", type: "INT") {
				constraints(nullable: "false")
			}

			column(name: "cuenta_contable_id", type: "BIGINT")

			column(name: "plazo", type: "INT") {
				constraints(nullable: "false")
			}

			column(name: "tasa_de_inversion", type: "DECIMAL(19,2)")

			column(name: "dias_inversion_isr", type: "INT") {
				constraints(nullable: "false")
			}

			column(name: "tasa_isr", type: "DECIMAL(19,2)") {
				constraints(nullable: "false")
			}
		}
	}

	changeSet(author: "rcancino (generated)", id: "1438439387978-21") {
		createTable(tableName: "cuenta_contable") {
			column(autoIncrement: "true", name: "id", type: "BIGINT") {
				constraints(nullable: "false", primaryKey: "true")
			}

			column(name: "version", type: "BIGINT") {
				constraints(nullable: "false")
			}

			column(name: "clave", type: "VARCHAR(100)")

			column(name: "date_created", type: "DATETIME") {
				constraints(nullable: "false")
			}

			column(name: "de_resultado", type: "BIT") {
				constraints(nullable: "false")
			}

			column(name: "descripcion", type: "LONGTEXT") {
				constraints(nullable: "false")
			}

			column(name: "detalle", type: "BIT") {
				constraints(nullable: "false")
			}

			column(name: "last_updated", type: "DATETIME") {
				constraints(nullable: "false")
			}

			column(name: "naturaleza", type: "VARCHAR(9)") {
				constraints(nullable: "false")
			}

			column(name: "padre_id", type: "BIGINT")

			column(name: "presentacion_contable", type: "BIT") {
				constraints(nullable: "false")
			}

			column(name: "presentacion_financiera", type: "BIT") {
				constraints(nullable: "false")
			}

			column(name: "presentacion_fiscal", type: "BIT") {
				constraints(nullable: "false")
			}

			column(name: "presentacion_presupuestal", type: "BIT") {
				constraints(nullable: "false")
			}

			column(name: "sub_tipo", type: "VARCHAR(255)")

			column(name: "tipo", type: "VARCHAR(7)") {
				constraints(nullable: "false")
			}
		}
	}

	changeSet(author: "rcancino (generated)", id: "1438439387978-22") {
		createTable(tableName: "cuenta_de_gastos") {
			column(autoIncrement: "true", name: "id", type: "BIGINT") {
				constraints(nullable: "false", primaryKey: "true")
			}

			column(name: "version", type: "BIGINT") {
				constraints(nullable: "false")
			}

			column(name: "comentario", type: "VARCHAR(250)")

			column(name: "embarque_id", type: "BIGINT") {
				constraints(nullable: "false")
			}

			column(name: "fecha", type: "DATETIME") {
				constraints(nullable: "false")
			}

			column(name: "importe", type: "DECIMAL(19,2)") {
				constraints(nullable: "false")
			}

			column(name: "impuestos", type: "DECIMAL(19,2)") {
				constraints(nullable: "false")
			}

			column(name: "total", type: "DECIMAL(19,2)") {
				constraints(nullable: "false")
			}

			column(name: "referencia", type: "VARCHAR(255)")

			column(name: "proveedor_id", type: "BIGINT")
		}
	}

	changeSet(author: "rcancino (generated)", id: "1438439387978-23") {
		createTable(tableName: "cuenta_de_gastos_cuenta_por_pagar") {
			column(name: "cuenta_de_gastos_facturas_id", type: "BIGINT")

			column(name: "cuenta_por_pagar_id", type: "BIGINT")
		}
	}

	changeSet(author: "rcancino (generated)", id: "1438439387978-24") {
		createTable(tableName: "cuenta_de_gastos_generica") {
			column(autoIncrement: "true", name: "id", type: "BIGINT") {
				constraints(nullable: "false", primaryKey: "true")
			}

			column(name: "version", type: "BIGINT") {
				constraints(nullable: "false")
			}

			column(name: "comentario", type: "VARCHAR(250)")

			column(name: "date_created", type: "DATETIME") {
				constraints(nullable: "false")
			}

			column(name: "descuento", type: "DECIMAL(19,2)") {
				constraints(nullable: "false")
			}

			column(name: "fecha", type: "DATETIME") {
				constraints(nullable: "false")
			}

			column(name: "importe", type: "DECIMAL(19,2)") {
				constraints(nullable: "false")
			}

			column(name: "impuestos", type: "DECIMAL(19,2)") {
				constraints(nullable: "false")
			}

			column(name: "last_updated", type: "DATETIME") {
				constraints(nullable: "false")
			}

			column(name: "proveedor_id", type: "BIGINT")

			column(name: "rembolso", type: "DECIMAL(19,2)") {
				constraints(nullable: "false")
			}

			column(name: "retension", type: "DECIMAL(19,2)") {
				constraints(nullable: "false")
			}

			column(name: "retension_isr", type: "DECIMAL(19,2)") {
				constraints(nullable: "false")
			}

			column(name: "total", type: "DECIMAL(19,2)") {
				constraints(nullable: "false")
			}

			column(name: "otros", type: "DECIMAL(19,2)")
		}
	}

	changeSet(author: "rcancino (generated)", id: "1438439387978-25") {
		createTable(tableName: "cuenta_por_pagar") {
			column(autoIncrement: "true", name: "id", type: "BIGINT") {
				constraints(nullable: "false", primaryKey: "true")
			}

			column(name: "version", type: "BIGINT") {
				constraints(nullable: "false")
			}

			column(name: "analisis_costo", type: "DECIMAL(19,2)") {
				constraints(nullable: "false")
			}

			column(name: "comentario", type: "VARCHAR(200)")

			column(name: "date_created", type: "DATETIME") {
				constraints(nullable: "false")
			}

			column(name: "descuentos", type: "DECIMAL(19,2)") {
				constraints(nullable: "false")
			}

			column(name: "documento", type: "VARCHAR(255)") {
				constraints(nullable: "false")
			}

			column(name: "fecha", type: "DATETIME") {
				constraints(nullable: "false")
			}

			column(name: "importe", type: "DECIMAL(19,2)") {
				constraints(nullable: "false")
			}

			column(name: "impuestos", type: "DECIMAL(19,2)") {
				constraints(nullable: "false")
			}

			column(name: "last_updated", type: "DATETIME") {
				constraints(nullable: "false")
			}

			column(name: "moneda", type: "VARCHAR(255)") {
				constraints(nullable: "false")
			}

			column(name: "proveedor_id", type: "BIGINT") {
				constraints(nullable: "false")
			}

			column(name: "saldo", type: "DECIMAL(19,2)") {
				constraints(nullable: "false")
			}

			column(name: "sub_total", type: "DECIMAL(19,2)") {
				constraints(nullable: "false")
			}

			column(name: "tasa_de_impuesto", type: "DECIMAL(19,4)") {
				constraints(nullable: "false")
			}

			column(name: "tc", type: "DECIMAL(19,4)") {
				constraints(nullable: "false")
			}

			column(name: "total", type: "DECIMAL(19,2)") {
				constraints(nullable: "false")
			}

			column(name: "totalmn", type: "DECIMAL(19,2)") {
				constraints(nullable: "false")
			}

			column(name: "vencimiento", type: "DATETIME") {
				constraints(nullable: "false")
			}

			column(name: "class", type: "VARCHAR(255)") {
				constraints(nullable: "false")
			}

			column(name: "pedimento_id", type: "BIGINT")

			column(name: "ret_imp", type: "DECIMAL(19,2)")

			column(name: "ret_tasa", type: "DECIMAL(19,2)")

			column(name: "retension_isr", type: "DECIMAL(19,2)")

			column(name: "incrementable", type: "BIT")

			column(name: "cuenta_generica_id", type: "BIGINT")

			column(name: "descuento", type: "DECIMAL(19,2)")

			column(name: "rembolso", type: "DECIMAL(19,2)")

			column(name: "otros", type: "DECIMAL(19,2)")

			column(name: "provisionada", type: "BIGINT")
		}
	}

	changeSet(author: "rcancino (generated)", id: "1438439387978-26") {
		createTable(tableName: "cxcabono") {
			column(autoIncrement: "true", name: "id", type: "BIGINT") {
				constraints(nullable: "false", primaryKey: "true")
			}

			column(name: "version", type: "BIGINT") {
				constraints(nullable: "false")
			}

			column(name: "aplicado", type: "DECIMAL(19,2)") {
				constraints(nullable: "false")
			}

			column(name: "cliente_id", type: "BIGINT") {
				constraints(nullable: "false")
			}

			column(name: "comentario", type: "VARCHAR(200)")

			column(name: "disponible", type: "DECIMAL(19,2)") {
				constraints(nullable: "false")
			}

			column(name: "fecha", type: "DATETIME") {
				constraints(nullable: "false")
			}

			column(name: "importe", type: "DECIMAL(19,2)") {
				constraints(nullable: "false")
			}

			column(name: "impuesto", type: "DECIMAL(19,2)") {
				constraints(nullable: "false")
			}

			column(name: "impuesto_tasa", type: "DECIMAL(19,2)") {
				constraints(nullable: "false")
			}

			column(name: "moneda", type: "VARCHAR(255)") {
				constraints(nullable: "false")
			}

			column(name: "tc", type: "DECIMAL(19,4)") {
				constraints(nullable: "false")
			}

			column(name: "total", type: "DECIMAL(19,2)") {
				constraints(nullable: "false")
			}

			column(name: "class", type: "VARCHAR(255)") {
				constraints(nullable: "false")
			}

			column(name: "cfd_id", type: "BIGINT")

			column(name: "cuenta_id", type: "BIGINT")

			column(name: "fecha_bancaria", type: "DATETIME")

			column(name: "forma_de_pago", type: "VARCHAR(13)")

			column(name: "referencia_bancaria", type: "VARCHAR(100)")

			column(name: "tipo", type: "VARCHAR(12)")

			column(name: "descuento", type: "DECIMAL(19,2)")

			column(name: "ingreso_id", type: "BIGINT")
		}
	}

	changeSet(author: "rcancino (generated)", id: "1438439387978-27") {
		createTable(tableName: "cxcaplicacion") {
			column(autoIncrement: "true", name: "id", type: "BIGINT") {
				constraints(nullable: "false", primaryKey: "true")
			}

			column(name: "version", type: "BIGINT") {
				constraints(nullable: "false")
			}

			column(name: "abono_id", type: "BIGINT") {
				constraints(nullable: "false")
			}

			column(name: "comentario", type: "VARCHAR(200)")

			column(name: "date_created", type: "DATETIME") {
				constraints(nullable: "false")
			}

			column(name: "fecha", type: "DATETIME") {
				constraints(nullable: "false")
			}

			column(name: "importe", type: "DECIMAL(19,2)") {
				constraints(nullable: "false")
			}

			column(name: "impuesto", type: "DECIMAL(19,2)") {
				constraints(nullable: "false")
			}

			column(name: "impuesto_tasa", type: "DECIMAL(19,2)") {
				constraints(nullable: "false")
			}

			column(name: "last_updated", type: "DATETIME") {
				constraints(nullable: "false")
			}

			column(name: "total", type: "DECIMAL(19,2)") {
				constraints(nullable: "false")
			}

			column(name: "aplicaciones_idx", type: "INT")

			column(name: "factura_id", type: "BIGINT")
		}
	}

	changeSet(author: "rcancino (generated)", id: "1438439387978-28") {
		createTable(tableName: "cxcnota_det") {
			column(autoIncrement: "true", name: "id", type: "BIGINT") {
				constraints(nullable: "false", primaryKey: "true")
			}

			column(name: "version", type: "BIGINT") {
				constraints(nullable: "false")
			}

			column(name: "cantidad", type: "DECIMAL(19,2)") {
				constraints(nullable: "false")
			}

			column(name: "comentario", type: "LONGTEXT") {
				constraints(nullable: "false")
			}

			column(name: "descripcion", type: "VARCHAR(200)") {
				constraints(nullable: "false")
			}

			column(name: "importe", type: "DECIMAL(19,2)") {
				constraints(nullable: "false")
			}

			column(name: "nota_id", type: "BIGINT") {
				constraints(nullable: "false")
			}

			column(name: "numero_de_identificacion", type: "VARCHAR(50)") {
				constraints(nullable: "false")
			}

			column(name: "unidad", type: "VARCHAR(100)") {
				constraints(nullable: "false")
			}

			column(name: "valor_unitario", type: "DECIMAL(19,2)") {
				constraints(nullable: "false")
			}

			column(name: "venta_id", type: "BIGINT")

			column(name: "partidas_idx", type: "INT")
		}
	}

	changeSet(author: "rcancino (generated)", id: "1438439387978-29") {
		createTable(tableName: "distribucion") {
			column(autoIncrement: "true", name: "id", type: "BIGINT") {
				constraints(nullable: "false", primaryKey: "true")
			}

			column(name: "version", type: "BIGINT") {
				constraints(nullable: "false")
			}

			column(name: "comentario", type: "VARCHAR(250)")

			column(name: "contenedores", type: "INT") {
				constraints(nullable: "false")
			}

			column(name: "date_created", type: "DATETIME") {
				constraints(nullable: "false")
			}

			column(name: "embarque_id", type: "BIGINT") {
				constraints(nullable: "false")
			}

			column(name: "fecha", type: "DATETIME") {
				constraints(nullable: "false")
			}

			column(name: "last_updated", type: "DATETIME") {
				constraints(nullable: "false")
			}
		}
	}

	changeSet(author: "rcancino (generated)", id: "1438439387978-30") {
		createTable(tableName: "distribucion_det") {
			column(autoIncrement: "true", name: "id", type: "BIGINT") {
				constraints(nullable: "false", primaryKey: "true")
			}

			column(name: "version", type: "BIGINT") {
				constraints(nullable: "false")
			}

			column(name: "cantidad", type: "DECIMAL(19,2)") {
				constraints(nullable: "false")
			}

			column(name: "cantidad_por_tarima", type: "DECIMAL(19,3)") {
				constraints(nullable: "false")
			}

			column(name: "comentarios", type: "VARCHAR(255)")

			column(name: "contenedor", type: "VARCHAR(255)") {
				constraints(nullable: "false")
			}

			column(name: "distribucion_id", type: "BIGINT") {
				constraints(nullable: "false")
			}

			column(name: "embarque_det_id", type: "BIGINT") {
				constraints(nullable: "false")
			}

			column(name: "kilos_netos", type: "DECIMAL(19,2)") {
				constraints(nullable: "false")
			}

			column(name: "sucursal", type: "VARCHAR(30)") {
				constraints(nullable: "false")
			}

			column(name: "tarimas", type: "INT") {
				constraints(nullable: "false")
			}

			column(name: "partidas_idx", type: "INT")

			column(name: "instrucciones", type: "VARCHAR(150)")

			column(name: "fecha_de_entrada", type: "DATETIME")

			column(name: "programacion_de_entrega", type: "DATETIME")
		}
	}

	changeSet(author: "rcancino (generated)", id: "1438439387978-31") {
		createTable(tableName: "embarque") {
			column(autoIncrement: "true", name: "id", type: "BIGINT") {
				constraints(nullable: "false", primaryKey: "true")
			}

			column(name: "version", type: "BIGINT") {
				constraints(nullable: "false")
			}

			column(name: "aduana_id", type: "BIGINT") {
				constraints(nullable: "false")
			}

			column(name: "bl", type: "VARCHAR(100)") {
				constraints(nullable: "false")
			}

			column(name: "comentario", type: "VARCHAR(250)")

			column(name: "contenedores", type: "INT") {
				constraints(nullable: "false")
			}

			column(name: "date_created", type: "DATETIME") {
				constraints(nullable: "false")
			}

			column(name: "fecha_embarque", type: "DATETIME") {
				constraints(nullable: "false")
			}

			column(name: "ingreso_aduana", type: "DATETIME")

			column(name: "last_updated", type: "DATETIME") {
				constraints(nullable: "false")
			}

			column(name: "liberado", type: "DATETIME")

			column(name: "moneda", type: "VARCHAR(255)") {
				constraints(nullable: "false")
			}

			column(name: "nombre", type: "VARCHAR(255)") {
				constraints(nullable: "false")
			}

			column(name: "proveedor_id", type: "BIGINT") {
				constraints(nullable: "false")
			}

			column(name: "tc", type: "DECIMAL(19,4)") {
				constraints(nullable: "false")
			}
		}
	}

	changeSet(author: "rcancino (generated)", id: "1438439387978-32") {
		createTable(tableName: "embarque_det") {
			column(autoIncrement: "true", name: "id", type: "BIGINT") {
				constraints(nullable: "false", primaryKey: "true")
			}

			column(name: "version", type: "BIGINT") {
				constraints(nullable: "false")
			}

			column(defaultValueNumeric: "0.000", name: "cantidad", type: "DECIMAL(19,3)") {
				constraints(nullable: "false")
			}

			column(name: "compra_det_id", type: "BIGINT") {
				constraints(nullable: "false")
			}

			column(name: "contenedor", type: "VARCHAR(30)")

			column(name: "costo_bruto", type: "DECIMAL(19,2)") {
				constraints(nullable: "false")
			}

			column(name: "costo_neto", type: "DECIMAL(19,2)") {
				constraints(nullable: "false")
			}

			column(name: "costo_unitario_neto", type: "DECIMAL(19,4)") {
				constraints(nullable: "false")
			}

			column(name: "embarque_id", type: "BIGINT") {
				constraints(nullable: "false")
			}

			column(name: "factura_id", type: "BIGINT")

			column(name: "gastos_honorarios", type: "DECIMAL(19,4)") {
				constraints(nullable: "false")
			}

			column(name: "gastos_por_pedimento", type: "DECIMAL(19,2)") {
				constraints(nullable: "false")
			}

			column(name: "importe", type: "DECIMAL(19,2)") {
				constraints(nullable: "false")
			}

			column(name: "kilos_estimados", type: "DECIMAL(19,2)") {
				constraints(nullable: "false")
			}

			column(name: "kilos_netos", type: "DECIMAL(19,2)") {
				constraints(nullable: "false")
			}

			column(name: "pedimento_id", type: "BIGINT")

			column(name: "precio", type: "DECIMAL(19,2)") {
				constraints(nullable: "false")
			}

			column(name: "precio_de_venta", type: "DECIMAL(19,2)") {
				constraints(nullable: "false")
			}

			column(name: "producto_id", type: "BIGINT")

			column(name: "tarimas", type: "INT") {
				constraints(nullable: "false")
			}

			column(name: "TC", type: "DECIMAL(22,4)") {
				constraints(nullable: "false")
			}

			column(name: "partidas_idx", type: "INT")

			column(name: "incrementables", type: "DECIMAL(19,2)")

			column(name: "gramos", type: "DECIMAL(19,2)")
		}
	}

	changeSet(author: "rcancino (generated)", id: "1438439387978-33") {
		createTable(tableName: "empresa") {
			column(autoIncrement: "true", name: "id", type: "BIGINT") {
				constraints(nullable: "false", primaryKey: "true")
			}

			column(name: "version", type: "BIGINT") {
				constraints(nullable: "false")
			}

			column(name: "direccion_calle", type: "VARCHAR(200)")

			column(name: "direccion_codigo_postal", type: "VARCHAR(255)")

			column(name: "direccion_colonia", type: "VARCHAR(255)")

			column(name: "direccion_estado", type: "VARCHAR(255)")

			column(name: "direccion_municipio", type: "VARCHAR(255)")

			column(name: "direccion_numero_exterior", type: "VARCHAR(50)")

			column(name: "direccion_numero_interior", type: "VARCHAR(50)")

			column(name: "direccion_pais", type: "VARCHAR(100)")

			column(name: "nombre", type: "VARCHAR(200)") {
				constraints(nullable: "false")
			}

			column(name: "regimen", type: "VARCHAR(255)") {
				constraints(nullable: "false")
			}

			column(name: "rfc", type: "VARCHAR(255)") {
				constraints(nullable: "false")
			}

			column(name: "certificado_digital", type: "MEDIUMBLOB")

			column(name: "certificado_digital_pfx", type: "MEDIUMBLOB")

			column(name: "llave_privada", type: "MEDIUMBLOB")

			column(name: "numero_de_certificado", type: "VARCHAR(20)") {
				constraints(nullable: "false")
			}

			column(name: "password_pfx", type: "VARCHAR(255)") {
				constraints(nullable: "false")
			}
		}
	}

	changeSet(author: "rcancino (generated)", id: "1438439387978-34") {
		createTable(tableName: "folio_fiscal") {
			column(autoIncrement: "true", name: "id", type: "BIGINT") {
				constraints(nullable: "false", primaryKey: "true")
			}

			column(name: "version", type: "BIGINT") {
				constraints(nullable: "false")
			}

			column(name: "ano_aprobacion", type: "INT") {
				constraints(nullable: "false")
			}

			column(name: "asignacion", type: "DATETIME") {
				constraints(nullable: "false")
			}

			column(name: "directorio_almacenar", type: "VARCHAR(255)")

			column(name: "folio", type: "BIGINT") {
				constraints(nullable: "false")
			}

			column(name: "folio_final", type: "BIGINT") {
				constraints(nullable: "false")
			}

			column(name: "folio_inicial", type: "BIGINT") {
				constraints(nullable: "false")
			}

			column(name: "no_aprobacion", type: "INT") {
				constraints(nullable: "false")
			}

			column(name: "serie", type: "VARCHAR(255)") {
				constraints(nullable: "false")
			}
		}
	}

	changeSet(author: "rcancino (generated)", id: "1438439387978-35") {
		createTable(tableName: "linea") {
			column(autoIncrement: "true", name: "id", type: "BIGINT") {
				constraints(nullable: "false", primaryKey: "true")
			}

			column(name: "version", type: "BIGINT") {
				constraints(nullable: "false")
			}

			column(name: "date_created", type: "DATETIME") {
				constraints(nullable: "false")
			}

			column(name: "last_updated", type: "DATETIME") {
				constraints(nullable: "false")
			}

			column(name: "nombre", type: "VARCHAR(20)") {
				constraints(nullable: "false")
			}
		}
	}

	changeSet(author: "rcancino (generated)", id: "1438439387978-36") {
		createTable(tableName: "marca") {
			column(autoIncrement: "true", name: "id", type: "BIGINT") {
				constraints(nullable: "false", primaryKey: "true")
			}

			column(name: "version", type: "BIGINT") {
				constraints(nullable: "false")
			}

			column(name: "date_created", type: "DATETIME") {
				constraints(nullable: "false")
			}

			column(name: "last_updated", type: "DATETIME") {
				constraints(nullable: "false")
			}

			column(name: "nombre", type: "VARCHAR(20)") {
				constraints(nullable: "false")
			}
		}
	}

	changeSet(author: "rcancino (generated)", id: "1438439387978-37") {
		createTable(tableName: "movimiento_de_cuenta") {
			column(autoIncrement: "true", name: "id", type: "BIGINT") {
				constraints(nullable: "false", primaryKey: "true")
			}

			column(name: "version", type: "BIGINT") {
				constraints(nullable: "false")
			}

			column(name: "comentario", type: "VARCHAR(250)")

			column(name: "concepto", type: "VARCHAR(255)")

			column(name: "cuenta_id", type: "BIGINT") {
				constraints(nullable: "false")
			}

			column(name: "date_created", type: "DATETIME") {
				constraints(nullable: "false")
			}

			column(name: "fecha", type: "DATETIME") {
				constraints(nullable: "false")
			}

			column(name: "importe", type: "DECIMAL(19,2)") {
				constraints(nullable: "false")
			}

			column(name: "ingreso", type: "BIT") {
				constraints(nullable: "false")
			}

			column(name: "last_updated", type: "DATETIME") {
				constraints(nullable: "false")
			}

			column(name: "moneda", type: "VARCHAR(255)") {
				constraints(nullable: "false")
			}

			column(name: "origen", type: "VARCHAR(70)") {
				constraints(nullable: "false")
			}

			column(name: "referencia_bancaria", type: "VARCHAR(250)")

			column(name: "tc", type: "DECIMAL(19,4)") {
				constraints(nullable: "false")
			}

			column(name: "tipo", type: "VARCHAR(13)") {
				constraints(nullable: "false")
			}

			column(name: "cuenta_deudora_id", type: "BIGINT")
		}
	}

	changeSet(author: "rcancino (generated)", id: "1438439387978-38") {
		createTable(tableName: "pago_proveedor") {
			column(autoIncrement: "true", name: "id", type: "BIGINT") {
				constraints(nullable: "false", primaryKey: "true")
			}

			column(name: "version", type: "BIGINT") {
				constraints(nullable: "false")
			}

			column(name: "comentario", type: "VARCHAR(200)")

			column(name: "cuenta_id", type: "BIGINT") {
				constraints(nullable: "false")
			}

			column(name: "date_created", type: "DATETIME") {
				constraints(nullable: "false")
			}

			column(name: "egreso_id", type: "BIGINT") {
				constraints(nullable: "false")
			}

			column(name: "fecha", type: "DATETIME") {
				constraints(nullable: "false")
			}

			column(name: "last_updated", type: "DATETIME") {
				constraints(nullable: "false")
			}

			column(name: "requisicion_id", type: "BIGINT") {
				constraints(nullable: "false")
			}

			column(name: "tipo_de_cambio", type: "DECIMAL(19,2)")
		}
	}

	changeSet(author: "rcancino (generated)", id: "1438439387978-39") {
		createTable(tableName: "pedimento") {
			column(autoIncrement: "true", name: "id", type: "BIGINT") {
				constraints(nullable: "false", primaryKey: "true")
			}

			column(name: "version", type: "BIGINT") {
				constraints(nullable: "false")
			}

			column(name: "arancel", type: "DECIMAL(19,2)")

			column(name: "comentario", type: "VARCHAR(250)")

			column(name: "date_created", type: "DATETIME") {
				constraints(nullable: "false")
			}

			column(name: "dta", type: "DECIMAL(19,2)") {
				constraints(nullable: "false")
			}

			column(name: "fecha", type: "DATETIME") {
				constraints(nullable: "false")
			}

			column(name: "impuesto", type: "DECIMAL(19,2)") {
				constraints(nullable: "false")
			}

			column(name: "impuesto_tasa", type: "DECIMAL(19,2)") {
				constraints(nullable: "false")
			}

			column(name: "last_updated", type: "DATETIME") {
				constraints(nullable: "false")
			}

			column(name: "pedimento", type: "VARCHAR(50)") {
				constraints(nullable: "false")
			}

			column(name: "prevalidacion", type: "DECIMAL(19,2)") {
				constraints(nullable: "false")
			}

			column(name: "tipo_de_cambio", type: "DECIMAL(19,4)") {
				constraints(nullable: "false")
			}

			column(name: "proveedor_id", type: "BIGINT")

			column(name: "incrementable1_id", type: "BIGINT")

			column(name: "referenciacg", type: "VARCHAR(50)")
		}
	}

	changeSet(author: "rcancino (generated)", id: "1438439387978-40") {
		createTable(tableName: "poliza") {
			column(autoIncrement: "true", name: "id", type: "BIGINT") {
				constraints(nullable: "false", primaryKey: "true")
			}

			column(name: "version", type: "BIGINT") {
				constraints(nullable: "false")
			}

			column(name: "date_created", type: "DATETIME") {
				constraints(nullable: "false")
			}

			column(name: "debe", type: "DECIMAL(19,6)") {
				constraints(nullable: "false")
			}

			column(name: "descripcion", type: "VARCHAR(250)") {
				constraints(nullable: "false")
			}

			column(name: "fecha", type: "DATETIME") {
				constraints(nullable: "false")
			}

			column(name: "folio", type: "INT") {
				constraints(nullable: "false")
			}

			column(name: "haber", type: "DECIMAL(19,6)") {
				constraints(nullable: "false")
			}

			column(name: "last_updated", type: "DATETIME") {
				constraints(nullable: "false")
			}

			column(name: "tipo", type: "VARCHAR(20)")
		}
	}

	changeSet(author: "rcancino (generated)", id: "1438439387978-41") {
		createTable(tableName: "poliza_det") {
			column(autoIncrement: "true", name: "id", type: "BIGINT") {
				constraints(nullable: "false", primaryKey: "true")
			}

			column(name: "version", type: "BIGINT") {
				constraints(nullable: "false")
			}

			column(name: "asiento", type: "VARCHAR(255)") {
				constraints(nullable: "false")
			}

			column(name: "cuenta_id", type: "BIGINT") {
				constraints(nullable: "false")
			}

			column(name: "debe", type: "DECIMAL(19,2)") {
				constraints(nullable: "false")
			}

			column(name: "descripcion", type: "VARCHAR(255)") {
				constraints(nullable: "false")
			}

			column(name: "haber", type: "DECIMAL(19,2)") {
				constraints(nullable: "false")
			}

			column(name: "poliza_id", type: "BIGINT") {
				constraints(nullable: "false")
			}

			column(name: "referencia", type: "VARCHAR(255)") {
				constraints(nullable: "false")
			}

			column(name: "partidas_idx", type: "INT")

			column(name: "entidad", type: "VARCHAR(50)")

			column(name: "fecha", type: "DATETIME") {
				constraints(nullable: "false")
			}

			column(name: "origen", type: "BIGINT")

			column(name: "tipo", type: "VARCHAR(20)")

			column(name: "concepto", type: "VARCHAR(50)")
		}
	}

	changeSet(author: "rcancino (generated)", id: "1438439387978-42") {
		createTable(tableName: "producto") {
			column(autoIncrement: "true", name: "id", type: "BIGINT") {
				constraints(nullable: "false", primaryKey: "true")
			}

			column(name: "version", type: "BIGINT") {
				constraints(nullable: "false")
			}

			column(name: "acabado", type: "VARCHAR(255)")

			column(name: "ancho", type: "DECIMAL(19,2)") {
				constraints(nullable: "false")
			}

			column(name: "calibre", type: "INT") {
				constraints(nullable: "false")
			}

			column(name: "caras", type: "INT") {
				constraints(nullable: "false")
			}

			column(name: "clase_id", type: "BIGINT")

			column(name: "clave", type: "VARCHAR(10)") {
				constraints(nullable: "false")
			}

			column(name: "color", type: "VARCHAR(255)")

			column(name: "date_created", type: "DATETIME") {
				constraints(nullable: "false")
			}

			column(name: "descripcion", type: "VARCHAR(250)") {
				constraints(nullable: "false")
			}

			column(name: "gramos", type: "DECIMAL(19,2)") {
				constraints(nullable: "false")
			}

			column(name: "kilos", type: "DECIMAL(19,2)") {
				constraints(nullable: "false")
			}

			column(name: "largo", type: "DECIMAL(19,2)") {
				constraints(nullable: "false")
			}

			column(name: "last_updated", type: "DATETIME") {
				constraints(nullable: "false")
			}

			column(name: "linea_id", type: "BIGINT") {
				constraints(nullable: "false")
			}

			column(name: "m2", type: "DECIMAL(19,2)") {
				constraints(nullable: "false")
			}

			column(name: "marca_id", type: "BIGINT")

			column(name: "precio_contado", type: "DECIMAL(19,2)") {
				constraints(nullable: "false")
			}

			column(name: "precio_credito", type: "DECIMAL(19,2)") {
				constraints(nullable: "false")
			}

			column(name: "unidad_id", type: "BIGINT") {
				constraints(nullable: "false")
			}
		}
	}

	changeSet(author: "rcancino (generated)", id: "1438439387978-43") {
		createTable(tableName: "proveedor") {
			column(autoIncrement: "true", name: "id", type: "BIGINT") {
				constraints(nullable: "false", primaryKey: "true")
			}

			column(name: "version", type: "BIGINT") {
				constraints(nullable: "false")
			}

			column(name: "correo_electronico", type: "VARCHAR(255)")

			column(name: "date_created", type: "DATETIME") {
				constraints(nullable: "false")
			}

			column(name: "direccion_calle", type: "VARCHAR(200)")

			column(name: "direccion_codigo_postal", type: "VARCHAR(255)")

			column(name: "direccion_colonia", type: "VARCHAR(255)")

			column(name: "direccion_estado", type: "VARCHAR(255)")

			column(name: "direccion_municipio", type: "VARCHAR(255)")

			column(name: "direccion_numero_exterior", type: "VARCHAR(50)")

			column(name: "direccion_numero_interior", type: "VARCHAR(50)")

			column(name: "direccion_pais", type: "VARCHAR(100)")

			column(name: "factor_de_utilidad", type: "DECIMAL(19,4)")

			column(name: "last_updated", type: "DATETIME") {
				constraints(nullable: "false")
			}

			column(name: "linea_de_credito", type: "DECIMAL(19,2)") {
				constraints(nullable: "false")
			}

			column(name: "nacional", type: "BIT") {
				constraints(nullable: "false")
			}

			column(name: "nombre", type: "VARCHAR(255)") {
				constraints(nullable: "false")
			}

			column(name: "plazo", type: "INT") {
				constraints(nullable: "false")
			}

			column(name: "rfc", type: "VARCHAR(13)")

			column(name: "tipo_de_costeo", type: "VARCHAR(8)")

			column(name: "www", type: "VARCHAR(255)")

			column(defaultValueBoolean: "false", name: "vencimento_bl", type: "BIT") {
				constraints(nullable: "false")
			}

			column(name: "sub_cuenta_operativa", type: "VARCHAR(4)")

			column(name: "nacionalidad", type: "VARCHAR(255)")

			column(name: "pais_de_origen", type: "VARCHAR(255)")
		}
	}

	changeSet(author: "rcancino (generated)", id: "1438439387978-44") {
		createTable(tableName: "proveedor_producto") {
			column(autoIncrement: "true", name: "id", type: "BIGINT") {
				constraints(nullable: "false", primaryKey: "true")
			}

			column(name: "version", type: "BIGINT") {
				constraints(nullable: "false")
			}

			column(name: "codigo", type: "VARCHAR(250)")

			column(name: "costo_unitario", type: "DECIMAL(19,2)") {
				constraints(nullable: "false")
			}

			column(name: "descripcion", type: "VARCHAR(250)")

			column(name: "producto_id", type: "BIGINT") {
				constraints(nullable: "false")
			}

			column(name: "proveedor_id", type: "BIGINT") {
				constraints(nullable: "false")
			}

			column(name: "gramos", type: "DECIMAL(19,2)")
		}
	}

	changeSet(author: "rcancino (generated)", id: "1438439387978-45") {
		createTable(tableName: "requisicion") {
			column(autoIncrement: "true", name: "id", type: "BIGINT") {
				constraints(nullable: "false", primaryKey: "true")
			}

			column(name: "version", type: "BIGINT") {
				constraints(nullable: "false")
			}

			column(name: "comentario", type: "VARCHAR(200)") {
				constraints(nullable: "false")
			}

			column(defaultValue: " ", name: "concepto", type: "VARCHAR(25)") {
				constraints(nullable: "false")
			}

			column(name: "date_created", type: "DATETIME") {
				constraints(nullable: "false")
			}

			column(defaultValueNumeric: "0.000000", name: "descuento_financiero", type: "DECIMAL(19,6)") {
				constraints(nullable: "false")
			}

			column(name: "fecha", type: "DATETIME") {
				constraints(nullable: "false")
			}

			column(name: "fecha_del_pago", type: "DATETIME") {
				constraints(nullable: "false")
			}

			column(name: "ietu", type: "DECIMAL(19,2)") {
				constraints(nullable: "false")
			}

			column(name: "importe", type: "DECIMAL(19,2)") {
				constraints(nullable: "false")
			}

			column(name: "impuestos", type: "DECIMAL(19,2)") {
				constraints(nullable: "false")
			}

			column(name: "last_updated", type: "DATETIME") {
				constraints(nullable: "false")
			}

			column(name: "moneda", type: "VARCHAR(255)") {
				constraints(nullable: "false")
			}

			column(name: "proveedor_id", type: "BIGINT") {
				constraints(nullable: "false")
			}

			column(name: "retencion_flete", type: "DECIMAL(19,2)") {
				constraints(nullable: "false")
			}

			column(name: "retencion_honorarios", type: "DECIMAL(19,2)") {
				constraints(nullable: "false")
			}

			column(name: "retencionisr", type: "DECIMAL(19,2)") {
				constraints(nullable: "false")
			}

			column(name: "tc", type: "DECIMAL(19,4)") {
				constraints(nullable: "false")
			}

			column(name: "total", type: "DECIMAL(19,2)") {
				constraints(nullable: "false")
			}

			column(name: "forma_de_pago", type: "VARCHAR(255)") {
				constraints(nullable: "false")
			}
		}
	}

	changeSet(author: "rcancino (generated)", id: "1438439387978-46") {
		createTable(tableName: "requisicion_det") {
			column(autoIncrement: "true", name: "id", type: "BIGINT") {
				constraints(nullable: "false", primaryKey: "true")
			}

			column(name: "version", type: "BIGINT") {
				constraints(nullable: "false")
			}

			column(name: "date_created", type: "DATETIME") {
				constraints(nullable: "false")
			}

			column(name: "documento", type: "VARCHAR(20)") {
				constraints(nullable: "false")
			}

			column(name: "factura_id", type: "BIGINT")

			column(name: "fecha_documento", type: "DATETIME") {
				constraints(nullable: "false")
			}

			column(name: "ietu", type: "DECIMAL(19,2)") {
				constraints(nullable: "false")
			}

			column(name: "importe", type: "DECIMAL(19,2)") {
				constraints(nullable: "false")
			}

			column(name: "impuestos", type: "DECIMAL(19,2)") {
				constraints(nullable: "false")
			}

			column(name: "last_updated", type: "DATETIME") {
				constraints(nullable: "false")
			}

			column(name: "requisicion_id", type: "BIGINT") {
				constraints(nullable: "false")
			}

			column(name: "retencion_flete", type: "DECIMAL(19,2)") {
				constraints(nullable: "false")
			}

			column(name: "retencion_honorarios", type: "DECIMAL(19,2)") {
				constraints(nullable: "false")
			}

			column(name: "retencionisr", type: "DECIMAL(19,2)") {
				constraints(nullable: "false")
			}

			column(name: "total", type: "DECIMAL(19,2)") {
				constraints(nullable: "false")
			}

			column(name: "total_documento", type: "DECIMAL(19,2)") {
				constraints(nullable: "false")
			}

			column(name: "partidas_idx", type: "INT")

			column(name: "embarque_id", type: "BIGINT")

			column(name: "gastos_de_importacion", type: "DECIMAL(19,2)")

			column(name: "impuestos_aduanales", type: "DECIMAL(19,2)")
		}
	}

	changeSet(author: "rcancino (generated)", id: "1438439387978-47") {
		createTable(tableName: "role") {
			column(autoIncrement: "true", name: "id", type: "BIGINT") {
				constraints(nullable: "false", primaryKey: "true")
			}

			column(name: "version", type: "BIGINT") {
				constraints(nullable: "false")
			}

			column(name: "authority", type: "VARCHAR(255)") {
				constraints(nullable: "false")
			}
		}
	}

	changeSet(author: "rcancino (generated)", id: "1438439387978-48") {
		createTable(tableName: "saldo_de_cuenta") {
			column(autoIncrement: "true", name: "id", type: "BIGINT") {
				constraints(nullable: "false", primaryKey: "true")
			}

			column(name: "version", type: "BIGINT") {
				constraints(nullable: "false")
			}

			column(name: "cierre", type: "DATETIME")

			column(name: "cuenta_id", type: "BIGINT") {
				constraints(nullable: "false")
			}

			column(name: "date_created", type: "DATETIME") {
				constraints(nullable: "false")
			}

			column(name: "egresos", type: "DECIMAL(19,2)") {
				constraints(nullable: "false")
			}

			column(name: "ingresos", type: "DECIMAL(19,2)") {
				constraints(nullable: "false")
			}

			column(name: "last_updated", type: "DATETIME") {
				constraints(nullable: "false")
			}

			column(name: "mes", type: "INT") {
				constraints(nullable: "false")
			}

			column(name: "saldo_final", type: "DECIMAL(19,2)") {
				constraints(nullable: "false")
			}

			column(name: "saldo_inicial", type: "DECIMAL(19,2)") {
				constraints(nullable: "false")
			}

			column(name: "year", type: "INT") {
				constraints(nullable: "false")
			}

			column(name: "saldo_finalmn", type: "DECIMAL(19,2)") {
				constraints(nullable: "false")
			}

			column(name: "tc", type: "DECIMAL(19,6)") {
				constraints(nullable: "false")
			}
		}
	}

	changeSet(author: "rcancino (generated)", id: "1438439387978-49") {
		createTable(tableName: "saldo_por_cuenta_contable") {
			column(autoIncrement: "true", name: "id", type: "BIGINT") {
				constraints(nullable: "false", primaryKey: "true")
			}

			column(name: "version", type: "BIGINT") {
				constraints(nullable: "false")
			}

			column(name: "cierre", type: "DATETIME") {
				constraints(nullable: "false")
			}

			column(name: "cuenta_id", type: "BIGINT") {
				constraints(nullable: "false")
			}

			column(name: "date_created", type: "DATETIME") {
				constraints(nullable: "false")
			}

			column(name: "debe", type: "DECIMAL(19,6)") {
				constraints(nullable: "false")
			}

			column(name: "fecha", type: "DATETIME") {
				constraints(nullable: "false")
			}

			column(name: "haber", type: "DECIMAL(19,6)") {
				constraints(nullable: "false")
			}

			column(name: "last_updated", type: "DATETIME") {
				constraints(nullable: "false")
			}

			column(name: "mes", type: "INT") {
				constraints(nullable: "false")
			}

			column(name: "saldo_final", type: "DECIMAL(19,6)") {
				constraints(nullable: "false")
			}

			column(name: "saldo_inicial", type: "DECIMAL(19,6)") {
				constraints(nullable: "false")
			}

			column(name: "year", type: "INT") {
				constraints(nullable: "false")
			}
		}
	}

	changeSet(author: "rcancino (generated)", id: "1438439387978-50") {
		createTable(tableName: "tipo_de_cambio") {
			column(autoIncrement: "true", name: "id", type: "BIGINT") {
				constraints(nullable: "false", primaryKey: "true")
			}

			column(name: "version", type: "BIGINT") {
				constraints(nullable: "false")
			}

			column(name: "date_created", type: "DATETIME") {
				constraints(nullable: "false")
			}

			column(name: "factor", type: "DECIMAL(19,6)") {
				constraints(nullable: "false")
			}

			column(name: "fecha", type: "DATETIME") {
				constraints(nullable: "false")
			}

			column(name: "fuente", type: "VARCHAR(200)") {
				constraints(nullable: "false")
			}

			column(name: "last_updated", type: "DATETIME") {
				constraints(nullable: "false")
			}

			column(name: "moneda_fuente", type: "VARCHAR(255)") {
				constraints(nullable: "false")
			}

			column(name: "moneda_origen", type: "VARCHAR(255)") {
				constraints(nullable: "false")
			}
		}
	}

	changeSet(author: "rcancino (generated)", id: "1438439387978-51") {
		createTable(tableName: "traspaso") {
			column(autoIncrement: "true", name: "id", type: "BIGINT") {
				constraints(nullable: "false", primaryKey: "true")
			}

			column(name: "version", type: "BIGINT") {
				constraints(nullable: "false")
			}

			column(name: "comentario", type: "VARCHAR(255)") {
				constraints(nullable: "false")
			}

			column(name: "comision", type: "DECIMAL(19,2)") {
				constraints(nullable: "false")
			}

			column(name: "cuenta_destino_id", type: "BIGINT") {
				constraints(nullable: "false")
			}

			column(name: "cuenta_origen_id", type: "BIGINT") {
				constraints(nullable: "false")
			}

			column(name: "date_created", type: "DATETIME") {
				constraints(nullable: "false")
			}

			column(name: "fecha", type: "DATETIME") {
				constraints(nullable: "false")
			}

			column(name: "importe", type: "DECIMAL(19,2)") {
				constraints(nullable: "false")
			}

			column(name: "impuesto", type: "DECIMAL(19,2)") {
				constraints(nullable: "false")
			}

			column(name: "last_updated", type: "DATETIME") {
				constraints(nullable: "false")
			}

			column(name: "moneda", type: "VARCHAR(255)") {
				constraints(nullable: "false")
			}

			column(name: "class", type: "VARCHAR(255)") {
				constraints(nullable: "false")
			}

			column(name: "importe_isr", type: "DECIMAL(19,2)")

			column(name: "plazo", type: "INT")

			column(name: "rendimiento_calculado", type: "DECIMAL(19,2)")

			column(name: "rendimiento_fecha", type: "DATETIME")

			column(name: "rendimiento_impuesto", type: "DECIMAL(19,2)")

			column(name: "rendimiento_real", type: "DECIMAL(19,2)")

			column(name: "tasa", type: "DECIMAL(19,2)")

			column(name: "tasa_isr", type: "DECIMAL(19,2)")

			column(name: "vencimiento", type: "DATETIME")
		}
	}

	changeSet(author: "rcancino (generated)", id: "1438439387978-52") {
		createTable(tableName: "traspaso_movimiento_de_cuenta") {
			column(name: "traspaso_movimientos_id", type: "BIGINT")

			column(name: "movimiento_de_cuenta_id", type: "BIGINT")
		}
	}

	changeSet(author: "rcancino (generated)", id: "1438439387978-53") {
		createTable(tableName: "unidad") {
			column(autoIncrement: "true", name: "id", type: "BIGINT") {
				constraints(nullable: "false", primaryKey: "true")
			}

			column(name: "version", type: "BIGINT") {
				constraints(nullable: "false")
			}

			column(name: "clave", type: "VARCHAR(3)") {
				constraints(nullable: "false")
			}

			column(name: "factor", type: "DECIMAL(19,2)") {
				constraints(nullable: "false")
			}

			column(name: "nombre", type: "VARCHAR(50)") {
				constraints(nullable: "false")
			}
		}
	}

	changeSet(author: "rcancino (generated)", id: "1438439387978-54") {
		createTable(tableName: "user") {
			column(autoIncrement: "true", name: "id", type: "BIGINT") {
				constraints(nullable: "false", primaryKey: "true")
			}

			column(name: "version", type: "BIGINT") {
				constraints(nullable: "false")
			}

			column(name: "account_expired", type: "BIT") {
				constraints(nullable: "false")
			}

			column(name: "account_locked", type: "BIT") {
				constraints(nullable: "false")
			}

			column(name: "enabled", type: "BIT") {
				constraints(nullable: "false")
			}

			column(name: "password", type: "VARCHAR(255)") {
				constraints(nullable: "false")
			}

			column(name: "password_expired", type: "BIT") {
				constraints(nullable: "false")
			}

			column(name: "username", type: "VARCHAR(255)") {
				constraints(nullable: "false")
			}
		}
	}

	changeSet(author: "rcancino (generated)", id: "1438439387978-55") {
		createTable(tableName: "user_role") {
			column(name: "role_id", type: "BIGINT") {
				constraints(nullable: "false")
			}

			column(name: "user_id", type: "BIGINT") {
				constraints(nullable: "false")
			}
		}
	}

	changeSet(author: "rcancino (generated)", id: "1438439387978-56") {
		createTable(tableName: "venta") {
			column(autoIncrement: "true", name: "id", type: "BIGINT") {
				constraints(nullable: "false", primaryKey: "true")
			}

			column(name: "version", type: "BIGINT") {
				constraints(nullable: "false")
			}

			column(name: "cfd_id", type: "BIGINT")

			column(name: "cliente_id", type: "BIGINT") {
				constraints(nullable: "false")
			}

			column(name: "comentario", type: "LONGTEXT") {
				constraints(nullable: "false")
			}

			column(name: "cuenta_de_pago", type: "VARCHAR(4)") {
				constraints(nullable: "false")
			}

			column(name: "date_created", type: "DATETIME") {
				constraints(nullable: "false")
			}

			column(name: "descuentos", type: "DECIMAL(19,2)") {
				constraints(nullable: "false")
			}

			column(name: "fecha", type: "DATETIME") {
				constraints(nullable: "false")
			}

			column(name: "forma_de_pago", type: "VARCHAR(20)") {
				constraints(nullable: "false")
			}

			column(name: "importe", type: "DECIMAL(19,2)") {
				constraints(nullable: "false")
			}

			column(name: "impuestos", type: "DECIMAL(19,2)") {
				constraints(nullable: "false")
			}

			column(name: "kilos", type: "DECIMAL(19,3)") {
				constraints(nullable: "false")
			}

			column(name: "last_updated", type: "DATETIME") {
				constraints(nullable: "false")
			}

			column(name: "moneda", type: "VARCHAR(255)") {
				constraints(nullable: "false")
			}

			column(name: "plazo", type: "INT") {
				constraints(nullable: "false")
			}

			column(name: "saldo", type: "DECIMAL(19,2)")

			column(name: "subtotal", type: "DECIMAL(19,2)") {
				constraints(nullable: "false")
			}

			column(name: "tc", type: "DECIMAL(19,4)") {
				constraints(nullable: "false")
			}

			column(name: "total", type: "DECIMAL(19,2)") {
				constraints(nullable: "false")
			}

			column(name: "vencimiento", type: "DATETIME") {
				constraints(nullable: "false")
			}

			column(defaultValue: "VENTA", name: "tipo", type: "VARCHAR(13)") {
				constraints(nullable: "false")
			}

			column(name: "clase", type: "VARCHAR(40)")
		}
	}

	changeSet(author: "rcancino (generated)", id: "1438439387978-57") {
		createTable(tableName: "venta_det") {
			column(autoIncrement: "true", name: "id", type: "BIGINT") {
				constraints(nullable: "false", primaryKey: "true")
			}

			column(name: "version", type: "BIGINT") {
				constraints(nullable: "false")
			}

			column(name: "aduana", type: "VARCHAR(50)")

			column(name: "cantidad", type: "DECIMAL(19,3)") {
				constraints(nullable: "false")
			}

			column(name: "comentario", type: "LONGTEXT")

			column(name: "contenedor", type: "VARCHAR(255)")

			column(name: "costo", type: "DECIMAL(19,2)") {
				constraints(nullable: "false")
			}

			column(name: "date_created", type: "DATETIME") {
				constraints(nullable: "false")
			}

			column(name: "descuentos", type: "DECIMAL(19,2)") {
				constraints(nullable: "false")
			}

			column(name: "embarque_id", type: "BIGINT")

			column(name: "importe", type: "DECIMAL(19,2)") {
				constraints(nullable: "false")
			}

			column(name: "impuesto", type: "DECIMAL(19,2)") {
				constraints(nullable: "false")
			}

			column(name: "impuesto_tasa", type: "DECIMAL(19,2)") {
				constraints(nullable: "false")
			}

			column(name: "kilos", type: "DECIMAL(19,3)") {
				constraints(nullable: "false")
			}

			column(name: "last_updated", type: "DATETIME") {
				constraints(nullable: "false")
			}

			column(name: "precio", type: "DECIMAL(19,2)") {
				constraints(nullable: "false")
			}

			column(name: "producto_id", type: "BIGINT") {
				constraints(nullable: "false")
			}

			column(name: "subtotal", type: "DECIMAL(19,2)") {
				constraints(nullable: "false")
			}

			column(name: "venta_id", type: "BIGINT") {
				constraints(nullable: "false")
			}

			column(name: "partidas_idx", type: "INT")
		}
	}

	changeSet(author: "rcancino (generated)", id: "1438439387978-58") {
		addPrimaryKey(columnNames: "role_id, user_id", tableName: "user_role")
	}

	changeSet(author: "rcancino (generated)", id: "1438439387978-144") {
		createIndex(indexName: "nombre", tableName: "aduana", unique: "true") {
			column(name: "nombre")
		}
	}

	changeSet(author: "rcancino (generated)", id: "1438439387978-145") {
		createIndex(indexName: "nombre", tableName: "clase", unique: "true") {
			column(name: "nombre")
		}
	}

	changeSet(author: "rcancino (generated)", id: "1438439387978-146") {
		createIndex(indexName: "nombre", tableName: "cliente", unique: "true") {
			column(name: "nombre")
		}
	}

	changeSet(author: "rcancino (generated)", id: "1438439387978-147") {
		createIndex(indexName: "bl", tableName: "embarque", unique: "true") {
			column(name: "bl")
		}
	}

	changeSet(author: "rcancino (generated)", id: "1438439387978-148") {
		createIndex(indexName: "nombre", tableName: "empresa", unique: "true") {
			column(name: "nombre")
		}
	}

	changeSet(author: "rcancino (generated)", id: "1438439387978-149") {
		createIndex(indexName: "nombre", tableName: "linea", unique: "true") {
			column(name: "nombre")
		}
	}

	changeSet(author: "rcancino (generated)", id: "1438439387978-150") {
		createIndex(indexName: "nombre", tableName: "marca", unique: "true") {
			column(name: "nombre")
		}
	}

	changeSet(author: "rcancino (generated)", id: "1438439387978-151") {
		createIndex(indexName: "pedimento", tableName: "pedimento", unique: "true") {
			column(name: "pedimento")
		}
	}

	changeSet(author: "rcancino (generated)", id: "1438439387978-152") {
		createIndex(indexName: "clave", tableName: "producto", unique: "true") {
			column(name: "clave")
		}
	}

	changeSet(author: "rcancino (generated)", id: "1438439387978-153") {
		createIndex(indexName: "authority_unique_1353003878645", tableName: "role", unique: "true") {
			column(name: "authority")
		}
	}

	changeSet(author: "rcancino (generated)", id: "1438439387978-154") {
		createIndex(indexName: "clave", tableName: "unidad", unique: "true") {
			column(name: "clave")
		}
	}

	changeSet(author: "rcancino (generated)", id: "1438439387978-155") {
		createIndex(indexName: "nombre", tableName: "unidad", unique: "true") {
			column(name: "nombre")
		}
	}

	changeSet(author: "rcancino (generated)", id: "1438439387978-156") {
		createIndex(indexName: "username_unique_1353003878706", tableName: "user", unique: "true") {
			column(name: "username")
		}
	}

	changeSet(author: "rcancino (generated)", id: "1438439387978-59") {
		addForeignKeyConstraint(baseColumnNames: "pago_proveedor_id", baseTableName: "abono", baseTableSchemaName: "lx_imports", constraintName: "FK58522AF5571D194", deferrable: "false", initiallyDeferred: "false", onDelete: "NO ACTION", onUpdate: "NO ACTION", referencedColumnNames: "id", referencedTableName: "pago_proveedor", referencedTableSchemaName: "lx_imports", referencesUniqueColumn: "false")
	}

	changeSet(author: "rcancino (generated)", id: "1438439387978-60") {
		addForeignKeyConstraint(baseColumnNames: "proveedor_id", baseTableName: "abono", baseTableSchemaName: "lx_imports", constraintName: "FK58522AF3ED34B6B", deferrable: "false", initiallyDeferred: "false", onDelete: "NO ACTION", onUpdate: "NO ACTION", referencedColumnNames: "id", referencedTableName: "proveedor", referencedTableSchemaName: "lx_imports", referencesUniqueColumn: "false")
	}

	changeSet(author: "rcancino (generated)", id: "1438439387978-61") {
		addForeignKeyConstraint(baseColumnNames: "complemento_id", baseTableName: "anticipo", baseTableSchemaName: "lx_imports", constraintName: "FKE327DB47B35BF68F", deferrable: "false", initiallyDeferred: "false", onDelete: "NO ACTION", onUpdate: "NO ACTION", referencedColumnNames: "id", referencedTableName: "requisicion", referencedTableSchemaName: "lx_imports", referencesUniqueColumn: "false")
	}

	changeSet(author: "rcancino (generated)", id: "1438439387978-62") {
		addForeignKeyConstraint(baseColumnNames: "requisicion_id", baseTableName: "anticipo", baseTableSchemaName: "lx_imports", constraintName: "FKE327DB47D23C08CB", deferrable: "false", initiallyDeferred: "false", onDelete: "NO ACTION", onUpdate: "NO ACTION", referencedColumnNames: "id", referencedTableName: "requisicion", referencedTableSchemaName: "lx_imports", referencesUniqueColumn: "false")
	}

	changeSet(author: "rcancino (generated)", id: "1438439387978-63") {
		addForeignKeyConstraint(baseColumnNames: "sobrante_id", baseTableName: "anticipo", baseTableSchemaName: "lx_imports", constraintName: "FKE327DB47FD86B921", deferrable: "false", initiallyDeferred: "false", onDelete: "NO ACTION", onUpdate: "NO ACTION", referencedColumnNames: "id", referencedTableName: "movimiento_de_cuenta", referencedTableSchemaName: "lx_imports", referencesUniqueColumn: "false")
	}

	changeSet(author: "rcancino (generated)", id: "1438439387978-64") {
		addForeignKeyConstraint(baseColumnNames: "abono_id", baseTableName: "aplicacion", baseTableSchemaName: "lx_imports", constraintName: "FKA1A6EF0F4CC6ACB8", deferrable: "false", initiallyDeferred: "false", onDelete: "NO ACTION", onUpdate: "NO ACTION", referencedColumnNames: "id", referencedTableName: "abono", referencedTableSchemaName: "lx_imports", referencesUniqueColumn: "false")
	}

	changeSet(author: "rcancino (generated)", id: "1438439387978-65") {
		addForeignKeyConstraint(baseColumnNames: "factura_id", baseTableName: "aplicacion", baseTableSchemaName: "lx_imports", constraintName: "FKA1A6EF0F8281048D", deferrable: "false", initiallyDeferred: "false", onDelete: "NO ACTION", onUpdate: "NO ACTION", referencedColumnNames: "id", referencedTableName: "cuenta_por_pagar", referencedTableSchemaName: "lx_imports", referencesUniqueColumn: "false")
	}

	changeSet(author: "rcancino (generated)", id: "1438439387978-66") {
		addForeignKeyConstraint(baseColumnNames: "cargo_id", baseTableName: "cancelacion_de_cargo", baseTableSchemaName: "lx_imports", constraintName: "FKB0D98EF13EF319BB", deferrable: "false", initiallyDeferred: "false", onDelete: "NO ACTION", onUpdate: "NO ACTION", referencedColumnNames: "id", referencedTableName: "venta", referencedTableSchemaName: "lx_imports", referencesUniqueColumn: "false")
	}

	changeSet(author: "rcancino (generated)", id: "1438439387978-67") {
		addForeignKeyConstraint(baseColumnNames: "cuenta_id", baseTableName: "cheque", baseTableSchemaName: "lx_imports", constraintName: "FKAED8F221E5999646", deferrable: "false", initiallyDeferred: "false", onDelete: "NO ACTION", onUpdate: "NO ACTION", referencedColumnNames: "id", referencedTableName: "cuenta_bancaria", referencedTableSchemaName: "lx_imports", referencesUniqueColumn: "false")
	}

	changeSet(author: "rcancino (generated)", id: "1438439387978-68") {
		addForeignKeyConstraint(baseColumnNames: "egreso_id", baseTableName: "cheque", baseTableSchemaName: "lx_imports", constraintName: "FKAED8F221E8F7855A", deferrable: "false", initiallyDeferred: "false", onDelete: "NO ACTION", onUpdate: "NO ACTION", referencedColumnNames: "id", referencedTableName: "movimiento_de_cuenta", referencedTableSchemaName: "lx_imports", referencesUniqueColumn: "false")
	}

	changeSet(author: "rcancino (generated)", id: "1438439387978-69") {
		addForeignKeyConstraint(baseColumnNames: "cuenta_id", baseTableName: "comision", baseTableSchemaName: "lx_imports", constraintName: "FKDBE5C9BDE5999646", deferrable: "false", initiallyDeferred: "false", onDelete: "NO ACTION", onUpdate: "NO ACTION", referencedColumnNames: "id", referencedTableName: "cuenta_bancaria", referencedTableSchemaName: "lx_imports", referencesUniqueColumn: "false")
	}

	changeSet(author: "rcancino (generated)", id: "1438439387978-70") {
		addForeignKeyConstraint(baseColumnNames: "comision_movimientos_id", baseTableName: "comision_movimiento_de_cuenta", baseTableSchemaName: "lx_imports", constraintName: "FKD9559FDE1224F8E", deferrable: "false", initiallyDeferred: "false", onDelete: "NO ACTION", onUpdate: "NO ACTION", referencedColumnNames: "id", referencedTableName: "comision", referencedTableSchemaName: "lx_imports", referencesUniqueColumn: "false")
	}

	changeSet(author: "rcancino (generated)", id: "1438439387978-71") {
		addForeignKeyConstraint(baseColumnNames: "movimiento_de_cuenta_id", baseTableName: "comision_movimiento_de_cuenta", baseTableSchemaName: "lx_imports", constraintName: "FKD9559FDEA97124F", deferrable: "false", initiallyDeferred: "false", onDelete: "NO ACTION", onUpdate: "NO ACTION", referencedColumnNames: "id", referencedTableName: "movimiento_de_cuenta", referencedTableSchemaName: "lx_imports", referencesUniqueColumn: "false")
	}

	changeSet(author: "rcancino (generated)", id: "1438439387978-72") {
		addForeignKeyConstraint(baseColumnNames: "proveedor_id", baseTableName: "compra", baseTableSchemaName: "lx_imports", constraintName: "FKAF3F357E3ED34B6B", deferrable: "false", initiallyDeferred: "false", onDelete: "NO ACTION", onUpdate: "NO ACTION", referencedColumnNames: "id", referencedTableName: "proveedor", referencedTableSchemaName: "lx_imports", referencesUniqueColumn: "false")
	}

	changeSet(author: "rcancino (generated)", id: "1438439387978-73") {
		addForeignKeyConstraint(baseColumnNames: "cuenta_destino_id", baseTableName: "compra_de_moneda", baseTableSchemaName: "lx_imports", constraintName: "FK7DA3ED13C47D18DD", deferrable: "false", initiallyDeferred: "false", onDelete: "NO ACTION", onUpdate: "NO ACTION", referencedColumnNames: "id", referencedTableName: "cuenta_bancaria", referencedTableSchemaName: "lx_imports", referencesUniqueColumn: "false")
	}

	changeSet(author: "rcancino (generated)", id: "1438439387978-74") {
		addForeignKeyConstraint(baseColumnNames: "cuenta_origen_id", baseTableName: "compra_de_moneda", baseTableSchemaName: "lx_imports", constraintName: "FK7DA3ED13DBEA256D", deferrable: "false", initiallyDeferred: "false", onDelete: "NO ACTION", onUpdate: "NO ACTION", referencedColumnNames: "id", referencedTableName: "cuenta_bancaria", referencedTableSchemaName: "lx_imports", referencesUniqueColumn: "false")
	}

	changeSet(author: "rcancino (generated)", id: "1438439387978-75") {
		addForeignKeyConstraint(baseColumnNames: "ingreso_id", baseTableName: "compra_de_moneda", baseTableSchemaName: "lx_imports", constraintName: "FK7DA3ED13517ED21A", deferrable: "false", initiallyDeferred: "false", onDelete: "NO ACTION", onUpdate: "NO ACTION", referencedColumnNames: "id", referencedTableName: "movimiento_de_cuenta", referencedTableSchemaName: "lx_imports", referencesUniqueColumn: "false")
	}

	changeSet(author: "rcancino (generated)", id: "1438439387978-76") {
		addForeignKeyConstraint(baseColumnNames: "pago_proveedor_id", baseTableName: "compra_de_moneda", baseTableSchemaName: "lx_imports", constraintName: "FK7DA3ED135571D194", deferrable: "false", initiallyDeferred: "false", onDelete: "NO ACTION", onUpdate: "NO ACTION", referencedColumnNames: "id", referencedTableName: "pago_proveedor", referencedTableSchemaName: "lx_imports", referencesUniqueColumn: "false")
	}

	changeSet(author: "rcancino (generated)", id: "1438439387978-77") {
		addForeignKeyConstraint(baseColumnNames: "requisicion_id", baseTableName: "compra_de_moneda", baseTableSchemaName: "lx_imports", constraintName: "FK7DA3ED13D23C08CB", deferrable: "false", initiallyDeferred: "false", onDelete: "NO ACTION", onUpdate: "NO ACTION", referencedColumnNames: "id", referencedTableName: "requisicion", referencedTableSchemaName: "lx_imports", referencesUniqueColumn: "false")
	}

	changeSet(author: "rcancino (generated)", id: "1438439387978-78") {
		addForeignKeyConstraint(baseColumnNames: "compra_id", baseTableName: "compra_det", baseTableSchemaName: "lx_imports", constraintName: "FKE9B8F9D234E86829", deferrable: "false", initiallyDeferred: "false", onDelete: "NO ACTION", onUpdate: "NO ACTION", referencedColumnNames: "id", referencedTableName: "compra", referencedTableSchemaName: "lx_imports", referencesUniqueColumn: "false")
	}

	changeSet(author: "rcancino (generated)", id: "1438439387978-79") {
		addForeignKeyConstraint(baseColumnNames: "producto_id", baseTableName: "compra_det", baseTableSchemaName: "lx_imports", constraintName: "FKE9B8F9D2DE0E3529", deferrable: "false", initiallyDeferred: "false", onDelete: "NO ACTION", onUpdate: "NO ACTION", referencedColumnNames: "id", referencedTableName: "producto", referencedTableSchemaName: "lx_imports", referencesUniqueColumn: "false")
	}

	changeSet(author: "rcancino (generated)", id: "1438439387978-80") {
		addForeignKeyConstraint(baseColumnNames: "concepto_id", baseTableName: "concepto_de_gasto", baseTableSchemaName: "lx_imports", constraintName: "FKF0056C0E15440032", deferrable: "false", initiallyDeferred: "false", onDelete: "NO ACTION", onUpdate: "NO ACTION", referencedColumnNames: "id", referencedTableName: "cuenta_contable", referencedTableSchemaName: "lx_imports", referencesUniqueColumn: "false")
	}

	changeSet(author: "rcancino (generated)", id: "1438439387978-81") {
		addForeignKeyConstraint(baseColumnNames: "egreso_id", baseTableName: "concepto_de_gasto", baseTableSchemaName: "lx_imports", constraintName: "FKF0056C0EE8F7855A", deferrable: "false", initiallyDeferred: "false", onDelete: "NO ACTION", onUpdate: "NO ACTION", referencedColumnNames: "id", referencedTableName: "movimiento_de_cuenta", referencedTableSchemaName: "lx_imports", referencesUniqueColumn: "false")
	}

	changeSet(author: "rcancino (generated)", id: "1438439387978-82") {
		addForeignKeyConstraint(baseColumnNames: "factura_id", baseTableName: "concepto_de_gasto", baseTableSchemaName: "lx_imports", constraintName: "FKF0056C0E3074CC4B", deferrable: "false", initiallyDeferred: "false", onDelete: "NO ACTION", onUpdate: "NO ACTION", referencedColumnNames: "id", referencedTableName: "cuenta_por_pagar", referencedTableSchemaName: "lx_imports", referencesUniqueColumn: "false")
	}

	changeSet(author: "rcancino (generated)", id: "1438439387978-83") {
		addForeignKeyConstraint(baseColumnNames: "banco_id", baseTableName: "cuenta_bancaria", baseTableSchemaName: "lx_imports", constraintName: "FKFFDD8754CEEA82CB", deferrable: "false", initiallyDeferred: "false", onDelete: "NO ACTION", onUpdate: "NO ACTION", referencedColumnNames: "id", referencedTableName: "banco", referencedTableSchemaName: "lx_imports", referencesUniqueColumn: "false")
	}

	changeSet(author: "rcancino (generated)", id: "1438439387978-84") {
		addForeignKeyConstraint(baseColumnNames: "cuenta_contable_id", baseTableName: "cuenta_bancaria", baseTableSchemaName: "lx_imports", constraintName: "FKFFDD87541F383816", deferrable: "false", initiallyDeferred: "false", onDelete: "NO ACTION", onUpdate: "NO ACTION", referencedColumnNames: "id", referencedTableName: "cuenta_contable", referencedTableSchemaName: "lx_imports", referencesUniqueColumn: "false")
	}

	changeSet(author: "rcancino (generated)", id: "1438439387978-85") {
		addForeignKeyConstraint(baseColumnNames: "padre_id", baseTableName: "cuenta_contable", baseTableSchemaName: "lx_imports", constraintName: "FK4D4545A33DCE98D3", deferrable: "false", initiallyDeferred: "false", onDelete: "NO ACTION", onUpdate: "NO ACTION", referencedColumnNames: "id", referencedTableName: "cuenta_contable", referencedTableSchemaName: "lx_imports", referencesUniqueColumn: "false")
	}

	changeSet(author: "rcancino (generated)", id: "1438439387978-86") {
		addForeignKeyConstraint(baseColumnNames: "embarque_id", baseTableName: "cuenta_de_gastos", baseTableSchemaName: "lx_imports", constraintName: "FKB1377E26D7625569", deferrable: "false", initiallyDeferred: "false", onDelete: "NO ACTION", onUpdate: "NO ACTION", referencedColumnNames: "id", referencedTableName: "embarque", referencedTableSchemaName: "lx_imports", referencesUniqueColumn: "false")
	}

	changeSet(author: "rcancino (generated)", id: "1438439387978-87") {
		addForeignKeyConstraint(baseColumnNames: "proveedor_id", baseTableName: "cuenta_de_gastos", baseTableSchemaName: "lx_imports", constraintName: "FKB1377E263ED34B6B", deferrable: "false", initiallyDeferred: "false", onDelete: "NO ACTION", onUpdate: "NO ACTION", referencedColumnNames: "id", referencedTableName: "proveedor", referencedTableSchemaName: "lx_imports", referencesUniqueColumn: "false")
	}

	changeSet(author: "rcancino (generated)", id: "1438439387978-88") {
		addForeignKeyConstraint(baseColumnNames: "cuenta_de_gastos_facturas_id", baseTableName: "cuenta_de_gastos_cuenta_por_pagar", baseTableSchemaName: "lx_imports", constraintName: "FK8118481D6BE3279D", deferrable: "false", initiallyDeferred: "false", onDelete: "NO ACTION", onUpdate: "NO ACTION", referencedColumnNames: "id", referencedTableName: "cuenta_de_gastos", referencedTableSchemaName: "lx_imports", referencesUniqueColumn: "false")
	}

	changeSet(author: "rcancino (generated)", id: "1438439387978-89") {
		addForeignKeyConstraint(baseColumnNames: "cuenta_por_pagar_id", baseTableName: "cuenta_de_gastos_cuenta_por_pagar", baseTableSchemaName: "lx_imports", constraintName: "FK8118481DE92F8961", deferrable: "false", initiallyDeferred: "false", onDelete: "NO ACTION", onUpdate: "NO ACTION", referencedColumnNames: "id", referencedTableName: "cuenta_por_pagar", referencedTableSchemaName: "lx_imports", referencesUniqueColumn: "false")
	}

	changeSet(author: "rcancino (generated)", id: "1438439387978-90") {
		addForeignKeyConstraint(baseColumnNames: "proveedor_id", baseTableName: "cuenta_de_gastos_generica", baseTableSchemaName: "lx_imports", constraintName: "FK968A6F633ED34B6B", deferrable: "false", initiallyDeferred: "false", onDelete: "NO ACTION", onUpdate: "NO ACTION", referencedColumnNames: "id", referencedTableName: "proveedor", referencedTableSchemaName: "lx_imports", referencesUniqueColumn: "false")
	}

	changeSet(author: "rcancino (generated)", id: "1438439387978-91") {
		addForeignKeyConstraint(baseColumnNames: "cuenta_generica_id", baseTableName: "cuenta_por_pagar", baseTableSchemaName: "lx_imports", constraintName: "FK937313243BAF62AD", deferrable: "false", initiallyDeferred: "false", onDelete: "NO ACTION", onUpdate: "NO ACTION", referencedColumnNames: "id", referencedTableName: "cuenta_de_gastos_generica", referencedTableSchemaName: "lx_imports", referencesUniqueColumn: "false")
	}

	changeSet(author: "rcancino (generated)", id: "1438439387978-92") {
		addForeignKeyConstraint(baseColumnNames: "pedimento_id", baseTableName: "cuenta_por_pagar", baseTableSchemaName: "lx_imports", constraintName: "FK9373132456E1BCCB", deferrable: "false", initiallyDeferred: "false", onDelete: "NO ACTION", onUpdate: "NO ACTION", referencedColumnNames: "id", referencedTableName: "pedimento", referencedTableSchemaName: "lx_imports", referencesUniqueColumn: "false")
	}

	changeSet(author: "rcancino (generated)", id: "1438439387978-93") {
		addForeignKeyConstraint(baseColumnNames: "proveedor_id", baseTableName: "cuenta_por_pagar", baseTableSchemaName: "lx_imports", constraintName: "FK937313243ED34B6B", deferrable: "false", initiallyDeferred: "false", onDelete: "NO ACTION", onUpdate: "NO ACTION", referencedColumnNames: "id", referencedTableName: "proveedor", referencedTableSchemaName: "lx_imports", referencesUniqueColumn: "false")
	}

	changeSet(author: "rcancino (generated)", id: "1438439387978-94") {
		addForeignKeyConstraint(baseColumnNames: "cfd_id", baseTableName: "cxcabono", baseTableSchemaName: "lx_imports", constraintName: "FKA67508E1302DA9BC", deferrable: "false", initiallyDeferred: "false", onDelete: "NO ACTION", onUpdate: "NO ACTION", referencedColumnNames: "id", referencedTableName: "comprobante_fiscal", referencedTableSchemaName: "lx_imports", referencesUniqueColumn: "false")
	}

	changeSet(author: "rcancino (generated)", id: "1438439387978-95") {
		addForeignKeyConstraint(baseColumnNames: "cliente_id", baseTableName: "cxcabono", baseTableSchemaName: "lx_imports", constraintName: "FKA67508E1BACD0E2B", deferrable: "false", initiallyDeferred: "false", onDelete: "NO ACTION", onUpdate: "NO ACTION", referencedColumnNames: "id", referencedTableName: "cliente", referencedTableSchemaName: "lx_imports", referencesUniqueColumn: "false")
	}

	changeSet(author: "rcancino (generated)", id: "1438439387978-96") {
		addForeignKeyConstraint(baseColumnNames: "cuenta_id", baseTableName: "cxcabono", baseTableSchemaName: "lx_imports", constraintName: "FKA67508E1E5999646", deferrable: "false", initiallyDeferred: "false", onDelete: "NO ACTION", onUpdate: "NO ACTION", referencedColumnNames: "id", referencedTableName: "cuenta_bancaria", referencedTableSchemaName: "lx_imports", referencesUniqueColumn: "false")
	}

	changeSet(author: "rcancino (generated)", id: "1438439387978-97") {
		addForeignKeyConstraint(baseColumnNames: "ingreso_id", baseTableName: "cxcabono", baseTableSchemaName: "lx_imports", constraintName: "FKA67508E1517ED21A", deferrable: "false", initiallyDeferred: "false", onDelete: "NO ACTION", onUpdate: "NO ACTION", referencedColumnNames: "id", referencedTableName: "movimiento_de_cuenta", referencedTableSchemaName: "lx_imports", referencesUniqueColumn: "false")
	}

	changeSet(author: "rcancino (generated)", id: "1438439387978-98") {
		addForeignKeyConstraint(baseColumnNames: "abono_id", baseTableName: "cxcaplicacion", baseTableSchemaName: "lx_imports", constraintName: "FKE09181D7A7C28BB", deferrable: "false", initiallyDeferred: "false", onDelete: "NO ACTION", onUpdate: "NO ACTION", referencedColumnNames: "id", referencedTableName: "cxcabono", referencedTableSchemaName: "lx_imports", referencesUniqueColumn: "false")
	}

	changeSet(author: "rcancino (generated)", id: "1438439387978-99") {
		addForeignKeyConstraint(baseColumnNames: "factura_id", baseTableName: "cxcaplicacion", baseTableSchemaName: "lx_imports", constraintName: "FKE09181DB4EDD07F", deferrable: "false", initiallyDeferred: "false", onDelete: "NO ACTION", onUpdate: "NO ACTION", referencedColumnNames: "id", referencedTableName: "venta", referencedTableSchemaName: "lx_imports", referencesUniqueColumn: "false")
	}

	changeSet(author: "rcancino (generated)", id: "1438439387978-100") {
		addForeignKeyConstraint(baseColumnNames: "nota_id", baseTableName: "cxcnota_det", baseTableSchemaName: "lx_imports", constraintName: "FKCB5FD0D0A911A979", deferrable: "false", initiallyDeferred: "false", onDelete: "NO ACTION", onUpdate: "NO ACTION", referencedColumnNames: "id", referencedTableName: "cxcabono", referencedTableSchemaName: "lx_imports", referencesUniqueColumn: "false")
	}

	changeSet(author: "rcancino (generated)", id: "1438439387978-101") {
		addForeignKeyConstraint(baseColumnNames: "venta_id", baseTableName: "cxcnota_det", baseTableSchemaName: "lx_imports", constraintName: "FKCB5FD0D0C21FCD2B", deferrable: "false", initiallyDeferred: "false", onDelete: "NO ACTION", onUpdate: "NO ACTION", referencedColumnNames: "id", referencedTableName: "venta", referencedTableSchemaName: "lx_imports", referencesUniqueColumn: "false")
	}

	changeSet(author: "rcancino (generated)", id: "1438439387978-102") {
		addForeignKeyConstraint(baseColumnNames: "embarque_id", baseTableName: "distribucion", baseTableSchemaName: "lx_imports", constraintName: "FKA1C09435D7625569", deferrable: "false", initiallyDeferred: "false", onDelete: "NO ACTION", onUpdate: "NO ACTION", referencedColumnNames: "id", referencedTableName: "embarque", referencedTableSchemaName: "lx_imports", referencesUniqueColumn: "false")
	}

	changeSet(author: "rcancino (generated)", id: "1438439387978-103") {
		addForeignKeyConstraint(baseColumnNames: "distribucion_id", baseTableName: "distribucion_det", baseTableSchemaName: "lx_imports", constraintName: "FKEA6E250958C47989", deferrable: "false", initiallyDeferred: "false", onDelete: "NO ACTION", onUpdate: "NO ACTION", referencedColumnNames: "id", referencedTableName: "distribucion", referencedTableSchemaName: "lx_imports", referencesUniqueColumn: "false")
	}

	changeSet(author: "rcancino (generated)", id: "1438439387978-104") {
		addForeignKeyConstraint(baseColumnNames: "embarque_det_id", baseTableName: "distribucion_det", baseTableSchemaName: "lx_imports", constraintName: "FKEA6E250944372A9E", deferrable: "false", initiallyDeferred: "false", onDelete: "NO ACTION", onUpdate: "NO ACTION", referencedColumnNames: "id", referencedTableName: "embarque_det", referencedTableSchemaName: "lx_imports", referencesUniqueColumn: "false")
	}

	changeSet(author: "rcancino (generated)", id: "1438439387978-105") {
		addForeignKeyConstraint(baseColumnNames: "aduana_id", baseTableName: "embarque", baseTableSchemaName: "lx_imports", constraintName: "FK2EA54C761F121DA9", deferrable: "false", initiallyDeferred: "false", onDelete: "NO ACTION", onUpdate: "NO ACTION", referencedColumnNames: "id", referencedTableName: "aduana", referencedTableSchemaName: "lx_imports", referencesUniqueColumn: "false")
	}

	changeSet(author: "rcancino (generated)", id: "1438439387978-106") {
		addForeignKeyConstraint(baseColumnNames: "proveedor_id", baseTableName: "embarque", baseTableSchemaName: "lx_imports", constraintName: "FK2EA54C763ED34B6B", deferrable: "false", initiallyDeferred: "false", onDelete: "NO ACTION", onUpdate: "NO ACTION", referencedColumnNames: "id", referencedTableName: "proveedor", referencedTableSchemaName: "lx_imports", referencesUniqueColumn: "false")
	}

	changeSet(author: "rcancino (generated)", id: "1438439387978-107") {
		addForeignKeyConstraint(baseColumnNames: "compra_det_id", baseTableName: "embarque_det", baseTableSchemaName: "lx_imports", constraintName: "FK89CAD4CA1EA57ACE", deferrable: "false", initiallyDeferred: "false", onDelete: "NO ACTION", onUpdate: "NO ACTION", referencedColumnNames: "id", referencedTableName: "compra_det", referencedTableSchemaName: "lx_imports", referencesUniqueColumn: "false")
	}

	changeSet(author: "rcancino (generated)", id: "1438439387978-108") {
		addForeignKeyConstraint(baseColumnNames: "embarque_id", baseTableName: "embarque_det", baseTableSchemaName: "lx_imports", constraintName: "FK89CAD4CAD7625569", deferrable: "false", initiallyDeferred: "false", onDelete: "NO ACTION", onUpdate: "NO ACTION", referencedColumnNames: "id", referencedTableName: "embarque", referencedTableSchemaName: "lx_imports", referencesUniqueColumn: "false")
	}

	changeSet(author: "rcancino (generated)", id: "1438439387978-109") {
		addForeignKeyConstraint(baseColumnNames: "factura_id", baseTableName: "embarque_det", baseTableSchemaName: "lx_imports", constraintName: "FK89CAD4CA8281048D", deferrable: "false", initiallyDeferred: "false", onDelete: "NO ACTION", onUpdate: "NO ACTION", referencedColumnNames: "id", referencedTableName: "cuenta_por_pagar", referencedTableSchemaName: "lx_imports", referencesUniqueColumn: "false")
	}

	changeSet(author: "rcancino (generated)", id: "1438439387978-110") {
		addForeignKeyConstraint(baseColumnNames: "pedimento_id", baseTableName: "embarque_det", baseTableSchemaName: "lx_imports", constraintName: "FK89CAD4CA56E1BCCB", deferrable: "false", initiallyDeferred: "false", onDelete: "NO ACTION", onUpdate: "NO ACTION", referencedColumnNames: "id", referencedTableName: "pedimento", referencedTableSchemaName: "lx_imports", referencesUniqueColumn: "false")
	}

	changeSet(author: "rcancino (generated)", id: "1438439387978-111") {
		addForeignKeyConstraint(baseColumnNames: "producto_id", baseTableName: "embarque_det", baseTableSchemaName: "lx_imports", constraintName: "FK89CAD4CADE0E3529", deferrable: "false", initiallyDeferred: "false", onDelete: "NO ACTION", onUpdate: "NO ACTION", referencedColumnNames: "id", referencedTableName: "producto", referencedTableSchemaName: "lx_imports", referencesUniqueColumn: "false")
	}

	changeSet(author: "rcancino (generated)", id: "1438439387978-112") {
		addForeignKeyConstraint(baseColumnNames: "cuenta_deudora_id", baseTableName: "movimiento_de_cuenta", baseTableSchemaName: "lx_imports", constraintName: "FKE715E69CEB206422", deferrable: "false", initiallyDeferred: "false", onDelete: "NO ACTION", onUpdate: "NO ACTION", referencedColumnNames: "id", referencedTableName: "cuenta_contable", referencedTableSchemaName: "lx_imports", referencesUniqueColumn: "false")
	}

	changeSet(author: "rcancino (generated)", id: "1438439387978-113") {
		addForeignKeyConstraint(baseColumnNames: "cuenta_id", baseTableName: "movimiento_de_cuenta", baseTableSchemaName: "lx_imports", constraintName: "FKE715E69CE5999646", deferrable: "false", initiallyDeferred: "false", onDelete: "NO ACTION", onUpdate: "NO ACTION", referencedColumnNames: "id", referencedTableName: "cuenta_bancaria", referencedTableSchemaName: "lx_imports", referencesUniqueColumn: "false")
	}

	changeSet(author: "rcancino (generated)", id: "1438439387978-114") {
		addForeignKeyConstraint(baseColumnNames: "cuenta_id", baseTableName: "pago_proveedor", baseTableSchemaName: "lx_imports", constraintName: "FK5EB67CB8E5999646", deferrable: "false", initiallyDeferred: "false", onDelete: "NO ACTION", onUpdate: "NO ACTION", referencedColumnNames: "id", referencedTableName: "cuenta_bancaria", referencedTableSchemaName: "lx_imports", referencesUniqueColumn: "false")
	}

	changeSet(author: "rcancino (generated)", id: "1438439387978-115") {
		addForeignKeyConstraint(baseColumnNames: "egreso_id", baseTableName: "pago_proveedor", baseTableSchemaName: "lx_imports", constraintName: "FK5EB67CB8E8F7855A", deferrable: "false", initiallyDeferred: "false", onDelete: "NO ACTION", onUpdate: "NO ACTION", referencedColumnNames: "id", referencedTableName: "movimiento_de_cuenta", referencedTableSchemaName: "lx_imports", referencesUniqueColumn: "false")
	}

	changeSet(author: "rcancino (generated)", id: "1438439387978-116") {
		addForeignKeyConstraint(baseColumnNames: "requisicion_id", baseTableName: "pago_proveedor", baseTableSchemaName: "lx_imports", constraintName: "FK5EB67CB8D23C08CB", deferrable: "false", initiallyDeferred: "false", onDelete: "NO ACTION", onUpdate: "NO ACTION", referencedColumnNames: "id", referencedTableName: "requisicion", referencedTableSchemaName: "lx_imports", referencesUniqueColumn: "false")
	}

	changeSet(author: "rcancino (generated)", id: "1438439387978-117") {
		addForeignKeyConstraint(baseColumnNames: "incrementable1_id", baseTableName: "pedimento", baseTableSchemaName: "lx_imports", constraintName: "FK3D12D9D734C69FD", deferrable: "false", initiallyDeferred: "false", onDelete: "NO ACTION", onUpdate: "NO ACTION", referencedColumnNames: "id", referencedTableName: "cuenta_por_pagar", referencedTableSchemaName: "lx_imports", referencesUniqueColumn: "false")
	}

	changeSet(author: "rcancino (generated)", id: "1438439387978-118") {
		addForeignKeyConstraint(baseColumnNames: "proveedor_id", baseTableName: "pedimento", baseTableSchemaName: "lx_imports", constraintName: "FK3D12D9D73ED34B6B", deferrable: "false", initiallyDeferred: "false", onDelete: "NO ACTION", onUpdate: "NO ACTION", referencedColumnNames: "id", referencedTableName: "proveedor", referencedTableSchemaName: "lx_imports", referencesUniqueColumn: "false")
	}

	changeSet(author: "rcancino (generated)", id: "1438439387978-119") {
		addForeignKeyConstraint(baseColumnNames: "cuenta_id", baseTableName: "poliza_det", baseTableSchemaName: "lx_imports", constraintName: "FK84EDB1B7806F2751", deferrable: "false", initiallyDeferred: "false", onDelete: "NO ACTION", onUpdate: "NO ACTION", referencedColumnNames: "id", referencedTableName: "cuenta_contable", referencedTableSchemaName: "lx_imports", referencesUniqueColumn: "false")
	}

	changeSet(author: "rcancino (generated)", id: "1438439387978-120") {
		addForeignKeyConstraint(baseColumnNames: "poliza_id", baseTableName: "poliza_det", baseTableSchemaName: "lx_imports", constraintName: "FK84EDB1B71D8360C5", deferrable: "false", initiallyDeferred: "false", onDelete: "NO ACTION", onUpdate: "NO ACTION", referencedColumnNames: "id", referencedTableName: "poliza", referencedTableSchemaName: "lx_imports", referencesUniqueColumn: "false")
	}

	changeSet(author: "rcancino (generated)", id: "1438439387978-121") {
		addForeignKeyConstraint(baseColumnNames: "clase_id", baseTableName: "producto", baseTableSchemaName: "lx_imports", constraintName: "FKC42BD1606771106B", deferrable: "false", initiallyDeferred: "false", onDelete: "NO ACTION", onUpdate: "NO ACTION", referencedColumnNames: "id", referencedTableName: "clase", referencedTableSchemaName: "lx_imports", referencesUniqueColumn: "false")
	}

	changeSet(author: "rcancino (generated)", id: "1438439387978-122") {
		addForeignKeyConstraint(baseColumnNames: "linea_id", baseTableName: "producto", baseTableSchemaName: "lx_imports", constraintName: "FKC42BD160858E7D8B", deferrable: "false", initiallyDeferred: "false", onDelete: "NO ACTION", onUpdate: "NO ACTION", referencedColumnNames: "id", referencedTableName: "linea", referencedTableSchemaName: "lx_imports", referencesUniqueColumn: "false")
	}

	changeSet(author: "rcancino (generated)", id: "1438439387978-123") {
		addForeignKeyConstraint(baseColumnNames: "marca_id", baseTableName: "producto", baseTableSchemaName: "lx_imports", constraintName: "FKC42BD1604CFF872B", deferrable: "false", initiallyDeferred: "false", onDelete: "NO ACTION", onUpdate: "NO ACTION", referencedColumnNames: "id", referencedTableName: "marca", referencedTableSchemaName: "lx_imports", referencesUniqueColumn: "false")
	}

	changeSet(author: "rcancino (generated)", id: "1438439387978-124") {
		addForeignKeyConstraint(baseColumnNames: "unidad_id", baseTableName: "producto", baseTableSchemaName: "lx_imports", constraintName: "FKC42BD1606F7CE589", deferrable: "false", initiallyDeferred: "false", onDelete: "NO ACTION", onUpdate: "NO ACTION", referencedColumnNames: "id", referencedTableName: "unidad", referencedTableSchemaName: "lx_imports", referencesUniqueColumn: "false")
	}

	changeSet(author: "rcancino (generated)", id: "1438439387978-125") {
		addForeignKeyConstraint(baseColumnNames: "producto_id", baseTableName: "proveedor_producto", baseTableSchemaName: "lx_imports", constraintName: "FK7DDF91A1DE0E3529", deferrable: "false", initiallyDeferred: "false", onDelete: "NO ACTION", onUpdate: "NO ACTION", referencedColumnNames: "id", referencedTableName: "producto", referencedTableSchemaName: "lx_imports", referencesUniqueColumn: "false")
	}

	changeSet(author: "rcancino (generated)", id: "1438439387978-126") {
		addForeignKeyConstraint(baseColumnNames: "proveedor_id", baseTableName: "proveedor_producto", baseTableSchemaName: "lx_imports", constraintName: "FK7DDF91A13ED34B6B", deferrable: "false", initiallyDeferred: "false", onDelete: "NO ACTION", onUpdate: "NO ACTION", referencedColumnNames: "id", referencedTableName: "proveedor", referencedTableSchemaName: "lx_imports", referencesUniqueColumn: "false")
	}

	changeSet(author: "rcancino (generated)", id: "1438439387978-127") {
		addForeignKeyConstraint(baseColumnNames: "proveedor_id", baseTableName: "requisicion", baseTableSchemaName: "lx_imports", constraintName: "FK1791964D3ED34B6B", deferrable: "false", initiallyDeferred: "false", onDelete: "NO ACTION", onUpdate: "NO ACTION", referencedColumnNames: "id", referencedTableName: "proveedor", referencedTableSchemaName: "lx_imports", referencesUniqueColumn: "false")
	}

	changeSet(author: "rcancino (generated)", id: "1438439387978-128") {
		addForeignKeyConstraint(baseColumnNames: "embarque_id", baseTableName: "requisicion_det", baseTableSchemaName: "lx_imports", constraintName: "FK2D405B21D7625569", deferrable: "false", initiallyDeferred: "false", onDelete: "NO ACTION", onUpdate: "NO ACTION", referencedColumnNames: "id", referencedTableName: "embarque", referencedTableSchemaName: "lx_imports", referencesUniqueColumn: "false")
	}

	changeSet(author: "rcancino (generated)", id: "1438439387978-129") {
		addForeignKeyConstraint(baseColumnNames: "factura_id", baseTableName: "requisicion_det", baseTableSchemaName: "lx_imports", constraintName: "FK2D405B218281048D", deferrable: "false", initiallyDeferred: "false", onDelete: "NO ACTION", onUpdate: "NO ACTION", referencedColumnNames: "id", referencedTableName: "cuenta_por_pagar", referencedTableSchemaName: "lx_imports", referencesUniqueColumn: "false")
	}

	changeSet(author: "rcancino (generated)", id: "1438439387978-130") {
		addForeignKeyConstraint(baseColumnNames: "requisicion_id", baseTableName: "requisicion_det", baseTableSchemaName: "lx_imports", constraintName: "FK2D405B21D23C08CB", deferrable: "false", initiallyDeferred: "false", onDelete: "NO ACTION", onUpdate: "NO ACTION", referencedColumnNames: "id", referencedTableName: "requisicion", referencedTableSchemaName: "lx_imports", referencesUniqueColumn: "false")
	}

	changeSet(author: "rcancino (generated)", id: "1438439387978-131") {
		addForeignKeyConstraint(baseColumnNames: "cuenta_id", baseTableName: "saldo_de_cuenta", baseTableSchemaName: "lx_imports", constraintName: "FK14219F0E5999646", deferrable: "false", initiallyDeferred: "false", onDelete: "NO ACTION", onUpdate: "NO ACTION", referencedColumnNames: "id", referencedTableName: "cuenta_bancaria", referencedTableSchemaName: "lx_imports", referencesUniqueColumn: "false")
	}

	changeSet(author: "rcancino (generated)", id: "1438439387978-132") {
		addForeignKeyConstraint(baseColumnNames: "cuenta_id", baseTableName: "saldo_por_cuenta_contable", baseTableSchemaName: "lx_imports", constraintName: "FK46AB8F61806F2751", deferrable: "false", initiallyDeferred: "false", onDelete: "NO ACTION", onUpdate: "NO ACTION", referencedColumnNames: "id", referencedTableName: "cuenta_contable", referencedTableSchemaName: "lx_imports", referencesUniqueColumn: "false")
	}

	changeSet(author: "rcancino (generated)", id: "1438439387978-133") {
		addForeignKeyConstraint(baseColumnNames: "cuenta_destino_id", baseTableName: "traspaso", baseTableSchemaName: "lx_imports", constraintName: "FK4C9DBF3DC47D18DD", deferrable: "false", initiallyDeferred: "false", onDelete: "NO ACTION", onUpdate: "NO ACTION", referencedColumnNames: "id", referencedTableName: "cuenta_bancaria", referencedTableSchemaName: "lx_imports", referencesUniqueColumn: "false")
	}

	changeSet(author: "rcancino (generated)", id: "1438439387978-134") {
		addForeignKeyConstraint(baseColumnNames: "cuenta_origen_id", baseTableName: "traspaso", baseTableSchemaName: "lx_imports", constraintName: "FK4C9DBF3DDBEA256D", deferrable: "false", initiallyDeferred: "false", onDelete: "NO ACTION", onUpdate: "NO ACTION", referencedColumnNames: "id", referencedTableName: "cuenta_bancaria", referencedTableSchemaName: "lx_imports", referencesUniqueColumn: "false")
	}

	changeSet(author: "rcancino (generated)", id: "1438439387978-135") {
		addForeignKeyConstraint(baseColumnNames: "movimiento_de_cuenta_id", baseTableName: "traspaso_movimiento_de_cuenta", baseTableSchemaName: "lx_imports", constraintName: "FKF761A5EA97124F", deferrable: "false", initiallyDeferred: "false", onDelete: "NO ACTION", onUpdate: "NO ACTION", referencedColumnNames: "id", referencedTableName: "movimiento_de_cuenta", referencedTableSchemaName: "lx_imports", referencesUniqueColumn: "false")
	}

	changeSet(author: "rcancino (generated)", id: "1438439387978-136") {
		addForeignKeyConstraint(baseColumnNames: "traspaso_movimientos_id", baseTableName: "traspaso_movimiento_de_cuenta", baseTableSchemaName: "lx_imports", constraintName: "FKF761A5EFE889F8E", deferrable: "false", initiallyDeferred: "false", onDelete: "NO ACTION", onUpdate: "NO ACTION", referencedColumnNames: "id", referencedTableName: "traspaso", referencedTableSchemaName: "lx_imports", referencesUniqueColumn: "false")
	}

	changeSet(author: "rcancino (generated)", id: "1438439387978-137") {
		addForeignKeyConstraint(baseColumnNames: "role_id", baseTableName: "user_role", baseTableSchemaName: "lx_imports", constraintName: "FK143BF46AF5916CE6", deferrable: "false", initiallyDeferred: "false", onDelete: "NO ACTION", onUpdate: "NO ACTION", referencedColumnNames: "id", referencedTableName: "role", referencedTableSchemaName: "lx_imports", referencesUniqueColumn: "false")
	}

	changeSet(author: "rcancino (generated)", id: "1438439387978-138") {
		addForeignKeyConstraint(baseColumnNames: "user_id", baseTableName: "user_role", baseTableSchemaName: "lx_imports", constraintName: "FK143BF46A9ABC30C6", deferrable: "false", initiallyDeferred: "false", onDelete: "NO ACTION", onUpdate: "NO ACTION", referencedColumnNames: "id", referencedTableName: "user", referencedTableSchemaName: "lx_imports", referencesUniqueColumn: "false")
	}

	changeSet(author: "rcancino (generated)", id: "1438439387978-139") {
		addForeignKeyConstraint(baseColumnNames: "cfd_id", baseTableName: "venta", baseTableSchemaName: "lx_imports", constraintName: "FK6AE6A4C302DA9BC", deferrable: "false", initiallyDeferred: "false", onDelete: "NO ACTION", onUpdate: "NO ACTION", referencedColumnNames: "id", referencedTableName: "comprobante_fiscal", referencedTableSchemaName: "lx_imports", referencesUniqueColumn: "false")
	}

	changeSet(author: "rcancino (generated)", id: "1438439387978-140") {
		addForeignKeyConstraint(baseColumnNames: "cliente_id", baseTableName: "venta", baseTableSchemaName: "lx_imports", constraintName: "FK6AE6A4CBACD0E2B", deferrable: "false", initiallyDeferred: "false", onDelete: "NO ACTION", onUpdate: "NO ACTION", referencedColumnNames: "id", referencedTableName: "cliente", referencedTableSchemaName: "lx_imports", referencesUniqueColumn: "false")
	}

	changeSet(author: "rcancino (generated)", id: "1438439387978-141") {
		addForeignKeyConstraint(baseColumnNames: "embarque_id", baseTableName: "venta_det", baseTableSchemaName: "lx_imports", constraintName: "FKD9C517A06F838372", deferrable: "false", initiallyDeferred: "false", onDelete: "NO ACTION", onUpdate: "NO ACTION", referencedColumnNames: "id", referencedTableName: "embarque_det", referencedTableSchemaName: "lx_imports", referencesUniqueColumn: "false")
	}

	changeSet(author: "rcancino (generated)", id: "1438439387978-142") {
		addForeignKeyConstraint(baseColumnNames: "producto_id", baseTableName: "venta_det", baseTableSchemaName: "lx_imports", constraintName: "FKD9C517A0DE0E3529", deferrable: "false", initiallyDeferred: "false", onDelete: "NO ACTION", onUpdate: "NO ACTION", referencedColumnNames: "id", referencedTableName: "producto", referencedTableSchemaName: "lx_imports", referencesUniqueColumn: "false")
	}

	changeSet(author: "rcancino (generated)", id: "1438439387978-143") {
		addForeignKeyConstraint(baseColumnNames: "venta_id", baseTableName: "venta_det", baseTableSchemaName: "lx_imports", constraintName: "FKD9C517A0C21FCD2B", deferrable: "false", initiallyDeferred: "false", onDelete: "NO ACTION", onUpdate: "NO ACTION", referencedColumnNames: "id", referencedTableName: "venta", referencedTableSchemaName: "lx_imports", referencesUniqueColumn: "false")
	}
}
