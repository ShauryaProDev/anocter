package com.anocter;

import lombok.Getter;

@Getter
public enum BeepType {
    ALERT(new Beep(2000, 150)),
    WARNING(new Beep(1050, 150));

    private final Beep beep;

    BeepType(Beep beep) {
        this.beep = beep;
    }
}
