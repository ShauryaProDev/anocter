package com.anocter;

import lombok.Getter;

@Getter
public enum BeepType {
    ALERT(new Beep(2000, 250)),
    WARNING(new Beep(1000, 250));

    private final Beep beep;

    BeepType(Beep beep) {
        this.beep = beep;
    }
}
