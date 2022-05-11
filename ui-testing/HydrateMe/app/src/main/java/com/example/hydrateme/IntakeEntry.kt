package com.example.hydrateme

class IntakeEntry(qty: Int) {
    val timestamp: Long = System.currentTimeMillis();
    val intake = qty
}