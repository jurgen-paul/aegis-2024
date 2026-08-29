package com.example

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import com.example.model.RadarThreatLevel
import com.example.model.TargetClassification
import com.example.model.ThreatSeverity
import com.example.viewmodel.AgisViewModel
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertTrue
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [36])
class ExampleRobolectricTest {

  @Test
  fun `read string from context`() {
    val context = ApplicationProvider.getApplicationContext<Context>()
    val appName = context.getString(R.string.app_name)
    assertEquals("AGIS 2045", appName)
  }

  @Test
  fun `verify radar targets and biometrics initialization`() {
    val context = ApplicationProvider.getApplicationContext<android.app.Application>()
    val viewModel = AgisViewModel(context)

    val radarTargets = viewModel.radarTargets.value
    assertTrue("Radar targets should be populated", radarTargets.isNotEmpty())
    assertTrue("Should have hostile target", radarTargets.any { it.threatLevel == RadarThreatLevel.HOSTILE })

    val subjects = viewModel.subjectRegistry.value
    assertTrue("Subject registry should be populated", subjects.isNotEmpty())
    assertTrue("Should have red notice subject", subjects.any { it.isRedNotice })

    val activeFace = viewModel.activeFacialScan.value
    assertNotNull("Facial scan should be initialized", activeFace)
    assertEquals(68, activeFace?.landmarkCount)

    val activeVoice = viewModel.activeVoiceprintScan.value
    assertNotNull("Voiceprint scan should be initialized", activeVoice)

    val activeShadow = viewModel.activeShadowScan.value
    assertNotNull("Shadow scan should be initialized", activeShadow)

    val activeFootsteps = viewModel.activeFootstepsScan.value
    assertNotNull("Footsteps scan should be initialized", activeFootsteps)
    assertTrue((activeFootsteps?.cadenceSpm ?: 0) > 0)
  }
}

