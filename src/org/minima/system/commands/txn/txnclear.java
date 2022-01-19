package org.minima.system.commands.txn;

import org.minima.system.commands.Command;
import org.minima.system.commands.txn.txndb.TxnDB;
import org.minima.system.commands.txn.txndb.TxnRow;
import org.minima.utils.json.JSONObject;

public class txnclear extends Command {

	public txnclear() {
		super("txnclear","[id:] - Clear the Witness data");
	}
	
	@Override
	public JSONObject runCommand() throws Exception {
		JSONObject ret = getJSONReply();

		TxnDB db = TxnDB.getDB();
		
		String id 	= getParam("id");
		
		//Get the Transaction..
		TxnRow txnrow 	= db.getTransactionRow(getParam("id"));
		txnrow.clearWitness();
		
		JSONObject resp = new JSONObject();
		ret.put("response", txnrow.toJSON());
		
		return ret;
	}

	@Override
	public Command getFunction() {
		return new txnclear();
	}

}
