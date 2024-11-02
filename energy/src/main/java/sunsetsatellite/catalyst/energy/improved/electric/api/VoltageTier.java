package sunsetsatellite.catalyst.energy.improved.electric.api;

import net.minecraft.core.net.command.TextFormatting;

public enum VoltageTier {
	ULV("Ultra Low Voltage",1,8, TextFormatting.GRAY),
	LV("Low Voltage",9,32, TextFormatting.RED),
	MV("Medium Voltage",33,128, TextFormatting.ORANGE),
	HV("High Voltage",129,512, TextFormatting.YELLOW),
	EV("Extreme Voltage",513,1024, TextFormatting.BLUE),
	UV("Ultra Voltage",1025,2048, TextFormatting.PURPLE),
	OV("Over Voltage",2049,Integer.MAX_VALUE, TextFormatting.MAGENTA);

	VoltageTier(String voltageName, int minVoltage, int maxVoltage, TextFormatting color){
		this.voltageName = voltageName;
		this.minVoltage = minVoltage;
		this.maxVoltage = maxVoltage;
		this.color = color;
	}

	public final String voltageName;
	public final int minVoltage;
	public final int maxVoltage;
	public final TextFormatting color;

	public static VoltageTier get(int voltage){
		for (VoltageTier tier : values()) {
			if(voltage >= tier.minVoltage && voltage <= tier.maxVoltage){
				return tier;
			}
		}
		return null;
	}
}
