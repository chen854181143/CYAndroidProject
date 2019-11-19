package com.chenyang.androidproject.utils;

import com.joanzapata.iconify.Icon;

public enum IconModel implements Icon {
    // &#xe735  冰箱
    // &#xe736  榨汁机
    // &#xe73a  空调
    // &#xe73b  智能机器人
    // &#xe73c  吸尘器
    // &#xe73d  洗衣机
    // &#xe73e  电吹风
    icon_refrigerator('\ue735'),
    icon_juicer('\ue736'),
    icon_air_conditioning('\ue73a'),
    icon_smart_robot('\ue73b'),
    icon_vacuum_cleaner('\ue73c'),
    icon_washing_machine('\ue73d'),
    icon_hair_dryer('\ue73e');

    private char character;

    IconModel(char character) {
        this.character = character;
    }

    /**
     * 参考 font
     */
    @Override
    public String key() {
        return name().replace('_', '-');
    }

    @Override
    public char character() {
        return character;
    }
}
