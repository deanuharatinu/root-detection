package com.deanuharatinu.rootdetector

class RootedDeviceException(
  override val message: String = "Device indicated as rooted"
) : RuntimeException()