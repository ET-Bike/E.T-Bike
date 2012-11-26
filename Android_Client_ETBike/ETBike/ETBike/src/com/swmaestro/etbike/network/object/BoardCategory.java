package com.swmaestro.etbike.network.object;


public enum BoardCategory {
	HOME{
		@Override public String getDescription() {
			return "HOME";
		}
	},
	SCENE{
		@Override public String getDescription() {
			return "SCENE";
		}
	},
	SHARE{
		@Override public String getDescription() {
			return "SHARE";
		}
	},
	DEALIST{
		@Override public String getDescription() {
			return "DEALIST";
		}
	},
	FALLOFFAME{
		@Override public String getDescription() {
			return "FALLOFFAME";
		}
	},
	RIDEWITHME{
		@Override public String getDescription() {
			return "RIDEWITHME";
		}
	}
	;

	public abstract String getDescription();
}



