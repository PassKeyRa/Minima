/**
 * 
 */
package org.minima.kissvm.values;

import org.minima.kissvm.tokens.Token;
import org.minima.objects.base.MiniNumber;

/**
 * @author Spartacus Rex
 *
 */
public abstract class Value {
	
	/**
	 * The Only Value Types
	 */
	public static final int VALUE_HEX     = 1;
	public static final int VALUE_NUMBER  = 2;
	public static final int VALUE_SCRIPT  = 4;
	public static final int VALUE_BOOLEAN = 8;
	
	/**
	 * What type of Value is this..
	 * @return
	 */
	public abstract int getValueType();
	
	/**
	 * TRUE or FALSE
	 */
	public boolean isTrue() {
		return !isFalse();
	}
	
	public abstract boolean isFalse();
	
	/**
	 * Strict Check this type and throw an exception if not
	 */
	public void verifyType(int zType) throws IllegalArgumentException{
		if (getValueType() != zType) {
			throw new IllegalArgumentException("Incorrect value type, expected "+zType+" found "+getValueType());
		}
	}
	
	
	/**
	 * GLOBAL STATIC FUNCTION for creating a Value from any string
	 */
	public static Value getValue(String zValue){
		if(zValue.startsWith("[") && zValue.endsWith("]")) {
			//remove the square brackets..
			String sc = zValue.substring(1,zValue.length()-1);
			return new ScriptValue(sc);
			
		}else if(zValue.startsWith("0x")) {
			return new HEXValue(zValue);

		}else if(zValue.equals("TRUE")) {
			return BooleanValue.TRUE;

		}else if(zValue.equals("FALSE")) {
			return BooleanValue.FALSE;

		}else if(Token.isNumeric(zValue)){
			return new NumberValue(zValue);
		
		}else {
			throw new IllegalArgumentException("Invalid value : "+zValue);
		}
	}
	
	/**
	 * GLOBAL STATIC FUNCTION for telling the value type
	 */
	public static int getValueType(String zValue) throws IllegalArgumentException {
		if(zValue.startsWith("[") && zValue.endsWith("]")) {
			//Then initialise the value 
			return VALUE_SCRIPT;
			
		}else if(zValue.startsWith("0x")) {
			return VALUE_HEX;

		}else if(zValue.equals("TRUE")) {
			return VALUE_BOOLEAN;

		}else if(zValue.equals("FALSE")) {
			return VALUE_BOOLEAN;

		}else if(Token.isNumeric(zValue)){
			return VALUE_NUMBER;
	
		}else {
			throw new IllegalArgumentException("Invalid value type : "+zValue);
		}
	}
	
	/**
	 * Get the type as a string
	 * 
	 * @param zType
	 * @return
	 */
	public static String getValueTypeString(int zType) {
		if(zType == VALUE_BOOLEAN) {
			return "BOOLEAN";
		}else if(zType == VALUE_HEX) {
			return "HEX";
		}else if(zType == VALUE_NUMBER) {
			return "NUMBER";
		}else if(zType == VALUE_SCRIPT) {
			return "SCRIPT";
		}
		
		return "ERROR_UNKNOWN_TYPE";
	}
	
	/**
	 * Check that both values are of the same type and throw an exception if not
	 * @param zV1
	 * @param zV2
	 */
	public static void checkSameType(Value zV1,Value zV2) throws IllegalArgumentException {
		if(zV1.getValueType() != zV2.getValueType()) {
			throw new IllegalArgumentException("Operation requires that both value types MUST be the same");
		}
	}
	
	public static void checkSameType(Value zV1,Value zV2, int zType) throws IllegalArgumentException {
		if(zV1.getValueType() != zV2.getValueType()) {
			throw new IllegalArgumentException("Operation requires that both value types MUST be the same");
		}
		
		if(zV1.getValueType() != zType) {
			throw new IllegalArgumentException("Operation requires that both value types are of type "+zType);
		}
	}
	
	/**
	 * Test Program
	 */
	public static void main(String[] zArgs) {
		Value vv = new HEXValue ("0xF184A0A4295AD508CEC610C7430CE328F184A0A4295AD508CEC610C7430CE328");
		
		MiniNumber q = new MiniNumber("1");
		MiniNumber e = new MiniNumber("3");
		MiniNumber t = q.div(e);
		
		System.out.println(vv);
		
//		//Get the data..
//		BigInteger bb = vv.getRamNumber().getAsBigInteger();
//		BigInteger cc = bb.shiftRight(2);
//		
//		Value v2 = new Value (cc.toString());
//		System.out.println(v2.mNumber);
		
	}	
}
