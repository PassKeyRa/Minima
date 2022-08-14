package org.minima.system.commands.base;

import org.minima.objects.base.MiniData;
import org.minima.objects.base.MiniNumber;
import org.minima.system.commands.Command;
import org.minima.utils.json.JSONObject;

public class random extends Command {

	public random() {
		super("random","(size:) - Generate a random hash value, defaults to 32 bytes");
	}
	
	@Override
	public JSONObject runCommand() throws Exception {
		JSONObject ret = getJSONReply();
		
		//How big a random number
		MiniNumber size = getNumberParam("size", new MiniNumber(32));
		
		//Generate..
		MiniData rand = MiniData.getRandomData(size.getAsInt());
		
		JSONObject resp = new JSONObject();
		resp.put("size", size.toString());
		resp.put("random", rand.to0xString());
		
		ret.put("response", resp);
		
		return ret;
	}

	@Override
	public Command getFunction() {
		return new random();
	}

}
