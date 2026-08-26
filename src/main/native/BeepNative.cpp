
#include <windows.h>
#include <com_anocter_Beep.h>

JNIEXPORT void JNICALL Java_com_anocter_Beep_beep(JNIEnv *env, jclass clazz, jdouble frequency, jlong duration) {
  Beep(frequency, duration);
};