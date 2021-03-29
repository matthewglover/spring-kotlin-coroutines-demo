import io.gitlab.arturbosch.detekt.Detekt

plugins {
    id("com.ncorti.ktfmt.gradle")
}

ktfmt { kotlinLangStyle() }
