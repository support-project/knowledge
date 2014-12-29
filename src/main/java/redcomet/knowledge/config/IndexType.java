package redcomet.knowledge.config;

public enum IndexType {
	Knoeledge,
	KnowledgeFile;
	
	
	public int getValue() {
		return ordinal();
	}
	
	public static IndexType getType(int type) {
		IndexType[] values = values();
		return values[type];
	}

}
