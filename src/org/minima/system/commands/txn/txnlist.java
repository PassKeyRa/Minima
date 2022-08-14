package org.minima.system.commands.txn;

import java.util.ArrayList;

import org.minima.database.MinimaDB;
import org.minima.database.userprefs.txndb.TxnDB;
import org.minima.database.userprefs.txndb.TxnRow;
import org.minima.system.commands.Command;
import org.minima.system.commands.CommandException;
import org.minima.utils.json.JSONArray;
import org.minima.utils.json.JSONObject;

public class txnlist extends Command {

	public txnlist() {
		super("txnlist","(id:) - List current custom transactions");
	}
	
	@Override
	public JSONObject runCommand() throws Exception {
		JSONObject ret = getJSONReply();

		TxnDB db = MinimaDB.getDB().getCustomTxnDB();
		
		//The transaction
		String id = getParam("id","");
		
		if(id.equals("")) {
			//The transaction
			ArrayList<TxnRow> txns = db.listTxns();
			
			JSONArray arr = new JSONArray();
			for(TxnRow txnrow : txns) {
				arr.add(txnrow.toJSON());
			}
			
			ret.put("response", arr);
		}else {
			TxnRow txnrow 	= db.getTransactionRow(getParam("id"));
			if(txnrow == null) {
				throw new CommandException("Transaction not found : "+id);
			}
			
			ret.put("response", txnrow.toJSON());
		}
		
		return ret;
	}

	@Override
	public Command getFunction() {
		return new txnlist();
	}

}
