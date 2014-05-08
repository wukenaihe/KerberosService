package com.cgs.kerberos.handle;

import com.cgs.kerberos.bean.SecondRequest;
import com.cgs.kerberos.bean.SecondResponse;
import com.cgs.kerberos.exception.KerberosException;

public interface TgsProcessor {
	SecondResponse check(SecondRequest secondRequest) throws KerberosException;
}
