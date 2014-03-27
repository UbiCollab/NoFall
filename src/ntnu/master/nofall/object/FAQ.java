package ntnu.master.nofall.object;

import java.util.ArrayList;
import java.util.List;

public class FAQ {

	public String string;
	public final List<String> children = new ArrayList<String>();

	public FAQ(String string) {
		this.string = string;
	}

}
