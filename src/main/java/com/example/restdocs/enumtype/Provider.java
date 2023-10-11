package com.example.restdocs.enumtype;

public enum Provider implements EnumType {

    NAVER("naver"),
    KAKAO("kakao");

    private final String value;

    Provider(String value) {
        this.value = value;
    }

    @Override
    public String getName() {
        return name();
    }

    @Override
    public String getValue() {
        return value;
    }
}
