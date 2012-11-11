package com.etbike.server.domain.model;

public enum BoardCategory {
	HOME{
		@Override public String getDescription() {
			return "HOME";
		}
	},
	SCENE{
		@Override public String getDescription() {
			return "경치보기";
		}
	},
	SHARE{
		@Override public String getDescription() {
			return "공유하기";
		}
	},
	DEALIST{
		@Override public String getDescription() {
			return "DEALIST";
		}
	},
	FALLOFFAME{
		@Override public String getDescription() {
			return "명예의 전당";
		}
	},
	RIDEWITHME{
		@Override public String getDescription() {
			return "같이타기";
		}
	}
	;

	public abstract String getDescription();
}



