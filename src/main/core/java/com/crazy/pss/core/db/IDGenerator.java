package com.crazy.pss.core.db;

import org.apache.commons.codec.binary.Base64;

import com.fasterxml.uuid.EthernetAddress;
import com.fasterxml.uuid.Generators;
import com.fasterxml.uuid.impl.TimeBasedGenerator;
import com.fasterxml.uuid.impl.UUIDUtil;

/**
 * 产生22位的uuid类。
 * 
 *  modify reason, upgrade JUG2 to JUG3 and addition translate base64 char
 */
public class IDGenerator {

	private static TimeBasedGenerator uuid_gen = Generators
			.timeBasedGenerator(EthernetAddress.fromInterface());


	/**
	 * uuid产生函数
	 * @return 22位长度的uuid
	 */
	public static String createId() {
		byte[] buffer = new byte[16];
		UUIDUtil.toByteArray(uuid_gen.generate(), buffer);
		String encodeBase64URLSafeString = Base64
				.encodeBase64URLSafeString(buffer);
		return encodeBase64URLSafeString;
	}
}

