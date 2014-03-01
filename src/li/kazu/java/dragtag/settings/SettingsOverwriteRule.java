package li.kazu.java.dragtag.settings;

import java.awt.List;
import java.util.HashMap;

import li.kazu.java.dragtag.model.Language;
import li.kazu.java.dragtag.model.LanguageConstant;

public class SettingsOverwriteRule {

	private String label;
	private SettingsOverwriteRuleEnum value;
	
	public SettingsOverwriteRule(SettingsOverwriteRuleEnum value){
		this.label = Language.get().get( LanguageConstant.valueOf("SETTINGS_OVERWRITE_RULES_" + value.name()));
		this.value = value;
	}
	
	public String getLabel(){
		return this.label;
	}
	
	public SettingsOverwriteRuleEnum getValue(){
		return this.value;
	}
	
	@Override
	public String toString(){
		return this.getLabel();
	}

	
	private static HashMap<SettingsOverwriteRuleEnum, SettingsOverwriteRule> ruleList;
	
	public static SettingsOverwriteRule[] getList(){
		if(ruleList == null){
			ruleList = new HashMap<SettingsOverwriteRuleEnum, SettingsOverwriteRule>();
			for(int i = 0; i < SettingsOverwriteRuleEnum.values().length; i++){
				ruleList.put(SettingsOverwriteRuleEnum.values()[i], new SettingsOverwriteRule(SettingsOverwriteRuleEnum.values()[i]));
			}
		}
		SettingsOverwriteRule[] result = new SettingsOverwriteRule[ruleList.size()];
		ruleList.values().toArray(result);
		return result;
	}
	
	public static SettingsOverwriteRule getRule(SettingsOverwriteRuleEnum key){
		return ruleList.get(key);
	}
	
	
	public enum SettingsOverwriteRuleEnum{
		EVER,
		FILESIZE_GREATER
	}	
	
}
