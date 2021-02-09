package org.minima.kissvm.functions.sha;

import org.minima.kissvm.Contract;
import org.minima.kissvm.exceptions.ExecutionException;
import org.minima.kissvm.functions.MinimaFunction;
import org.minima.kissvm.values.HEXValue;
import org.minima.kissvm.values.Value;
import org.minima.objects.base.MiniData;
import org.minima.objects.proofs.Proof;
import org.minima.utils.Crypto;

public class CHAINSHA extends MinimaFunction {

	public CHAINSHA() {
		super("CHAINSHA");
	}
	
	@Override
	public Value runFunction(Contract zContract) throws ExecutionException {
		checkExactParamNumber(2);
		
		HEXValue val  = zContract.getHEXParam(0, this);
		MiniData data = val.getMiniData();

		//Get the hash data chain + 1 byte for left right 
		HEXValue chain = zContract.getHEXParam(1, this);
		
		//Bit Strength
		int bits;
		try {
			bits = Proof.getChainSHABits(chain.toString());
		} catch (Exception e) {
			throw new ExecutionException(e.toString());
		}
		
		//Hash the data
		byte[] hash = Crypto.getInstance().hashData(data.getData(), bits);
		MiniData finalhash = new MiniData(hash);
		
		//Create a proof..
		Proof chainproof = new Proof();

		//Hash the Input..		
		chainproof.setData(finalhash);
		chainproof.setProof(chain.getMiniData());
		
		//Return..
		return new HEXValue(chainproof.getFinalHash());
	}
	
	@Override
	public MinimaFunction getNewFunction() {
		return new CHAINSHA();
	}
}
