package com.example.certwatch.domain;

public sealed interface Status permits Reachable, Unreachable {

}
