package com.swmaestro.etbike.serverobject;


public enum BoardCategory {
	HOME{
		@Override public String getDescription() {
			return "HOME";
		}
	},
	SCENE{
		@Override public String getDescription() {
			return "寃쎌튂蹂닿린";
		}
	},
	SHARE{
		@Override public String getDescription() {
			return "怨듭쑀�섍린";
		}
	},
	DEALIST{
		@Override public String getDescription() {
			return "DEALIST";
		}
	},
	FALLOFFAME{
		@Override public String getDescription() {
			return "紐낆삁���꾨떦";
		}
	},
	RIDEWITHME{
		@Override public String getDescription() {
			return "媛숈씠��린";
		}
	}
	;

	public abstract String getDescription();
}



