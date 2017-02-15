package com.luxsoft.nomina

import com.luxsoft.lx.sat.BancoSat

class Asimilado {

	String nombre

	String rfc

    String curp 

	BancoSat bancoSat

	String numeroDeCuenta

	String clabe

	String formaDePago


    static constraints = {
    	rfc minSize:12, maxSize:13
        curp minSoze:1, maxSize:20
    	formaDePago inList: ['CHEQUE', 'TRANSFERENCIA']
    	bancoSat nullable:true
    	numeroDeCuenta nullable:true, maxSize: 16
    	clabe nullable:true, maxSize: 18, minSize: 18
    }

    String toString(){
        return "$nombre ($rfc)"
    }
}
