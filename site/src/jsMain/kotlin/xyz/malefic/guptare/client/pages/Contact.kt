package xyz.malefic.guptare.client.pages

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import com.varabyte.kobweb.compose.css.JustifyContent
import com.varabyte.kobweb.compose.css.TextAlign
import com.varabyte.kobweb.compose.foundation.layout.Arrangement
import com.varabyte.kobweb.compose.foundation.layout.Box
import com.varabyte.kobweb.compose.foundation.layout.Column
import com.varabyte.kobweb.compose.ui.Alignment
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.modifiers.backgroundColor
import com.varabyte.kobweb.compose.ui.modifiers.borderRadius
import com.varabyte.kobweb.compose.ui.modifiers.color
import com.varabyte.kobweb.compose.ui.modifiers.fillMaxWidth
import com.varabyte.kobweb.compose.ui.modifiers.gap
import com.varabyte.kobweb.compose.ui.modifiers.gridColumn
import com.varabyte.kobweb.compose.ui.modifiers.height
import com.varabyte.kobweb.compose.ui.modifiers.justifyContent
import com.varabyte.kobweb.compose.ui.modifiers.margin
import com.varabyte.kobweb.compose.ui.modifiers.minWidth
import com.varabyte.kobweb.compose.ui.modifiers.padding
import com.varabyte.kobweb.compose.ui.modifiers.textAlign
import com.varabyte.kobweb.compose.ui.toAttrs
import com.varabyte.kobweb.core.Page
import com.varabyte.kobweb.silk.components.forms.Button
import com.varabyte.kobweb.silk.components.layout.SimpleGrid
import com.varabyte.kobweb.silk.components.layout.numColumns
import com.varabyte.kobweb.silk.style.toModifier
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.jetbrains.compose.web.attributes.placeholder
import org.jetbrains.compose.web.css.percent
import org.jetbrains.compose.web.css.px
import org.jetbrains.compose.web.dom.Label
import org.jetbrains.compose.web.dom.P
import org.jetbrains.compose.web.dom.Text
import org.jetbrains.compose.web.dom.TextArea
import org.jetbrains.compose.web.dom.TextInput
import xyz.malefic.guptare.client.api.postContact
import xyz.malefic.guptare.client.pages.ContactSubmissionStatus.Error
import xyz.malefic.guptare.client.pages.ContactSubmissionStatus.Idle
import xyz.malefic.guptare.client.pages.ContactSubmissionStatus.Submitting
import xyz.malefic.guptare.client.pages.ContactSubmissionStatus.Success
import xyz.malefic.guptare.client.styles.AppColors
import xyz.malefic.guptare.client.styles.AppModifiers
import xyz.malefic.guptare.client.styles.AppRadius
import xyz.malefic.guptare.client.styles.AppSpacing
import xyz.malefic.guptare.client.styles.BodyLgStyle
import xyz.malefic.guptare.client.styles.BodyMdStyle
import xyz.malefic.guptare.client.styles.ContainerStyle
import xyz.malefic.guptare.client.styles.DisplayLgStyle
import xyz.malefic.guptare.client.styles.InputErrorStyle
import xyz.malefic.guptare.client.styles.InputLabelStyle
import xyz.malefic.guptare.client.styles.InputStyle
import xyz.malefic.guptare.client.styles.LabelMdStyle
import xyz.malefic.guptare.client.styles.LabelSmStyle
import xyz.malefic.guptare.client.styles.PrimaryButtonStyle
import xyz.malefic.guptare.client.styles.SectionStyle
import kotlin.time.Duration.Companion.milliseconds

private enum class ContactSubmissionStatus {
    Idle,
    Submitting,
    Success,
    Error,
}

@Page
@Composable
fun ContactPage() {
    val coroutineScope = rememberCoroutineScope()

    Box(
        SectionStyle.toModifier().padding(
            top = 40.px,
            bottom = AppSpacing.SectionGap,
        ),
        Alignment.Center,
    ) {
        Column(
            ContainerStyle
                .toModifier(),
            Arrangement.Center,
            Alignment.CenterHorizontally,
        ) {
            P(
                LabelSmStyle
                    .toModifier()
                    .backgroundColor(AppColors.PrimaryContainer)
                    .color(AppColors.OnPrimaryContainer)
                    .padding(topBottom = 4.px, leftRight = 16.px)
                    .borderRadius(AppRadius.Full)
                    .margin(bottom = 24.px)
                    .toAttrs(),
            ) {
                Text("Contact")
            }

            P(
                DisplayLgStyle
                    .toModifier()
                    .margin(bottom = 24.px)
                    .toAttrs(),
            ) {
                Text("Let's Connect")
            }

            P(
                BodyLgStyle
                    .toModifier()
                    .color(AppColors.OnSurfaceVariant)
                    .margin(bottom = 24.px)
                    .toAttrs(),
            ) {
                Text(
                    "Tell us what you need help with, and we'll follow up with the next best steps for buying, selling, or just getting oriented.",
                )
            }

            P(
                BodyMdStyle
                    .toModifier()
                    .color(AppColors.OnSurfaceVariant)
                    .margin(bottom = 0.px)
                    .toAttrs(),
            ) {
                Text("We usually respond by email or phone after reviewing your message.")
            }

            Box(
                Modifier.height(
                    AppSpacing.Gutter,
                ),
            )

            ContactFormCard(coroutineScope)
        }
    }
}

@Composable
private fun ContactFormCard(coroutineScope: kotlinx.coroutines.CoroutineScope) {
    val firstName = remember { mutableStateOf("") }
    val lastName = remember { mutableStateOf("") }
    val email = remember { mutableStateOf("") }
    val phone = remember { mutableStateOf("") }
    val message = remember { mutableStateOf("") }

    var status by remember { mutableStateOf(Idle) }

    LaunchedEffect(status) {
        if (status == Success ||
            status == Error
        ) {
            delay(2000.milliseconds)
            status = Idle
        }
    }

    fun phoneDigits(value: String): String {
        val digits = value.filter { it.isDigit() }
        return if (digits.length == 11 && digits.startsWith("1")) digits.substring(1) else digits
    }

    fun isValidEmail(value: String): Boolean = Regex("^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$").matches(value)

    fun isValidPhone(value: String): Boolean = phoneDigits(value).length == 10

    SimpleGrid(
        numColumns(1, 2, 3),
        Modifier
            .backgroundColor(AppColors.SurfaceHighest)
            .padding(48.px)
            .borderRadius(AppRadius.Xl)
            .then(AppModifiers.ElevatedShadow)
            .then(AppModifiers.CardBorder)
            .gap(AppSpacing s 4),
    ) {
        Column(Modifier.gridColumn("span 3")) {
            P(
                LabelMdStyle
                    .toModifier()
                    .color(AppColors.Primary)
                    .margin(bottom = 8.px)
                    .toAttrs(),
            ) {
                Text("Let's Connect")
            }

            P(
                BodyMdStyle
                    .toModifier()
                    .color(AppColors.OnSurfaceVariant)
                    .margin(bottom = 32.px)
                    .toAttrs(),
            ) {
                Text("Send a note and we'll get back to you with a clear next step.")
            }
        }

        ContactField("First Name", firstName, "Ruchika")
        ContactField("Last Name", lastName, "Gupta")
        ContactField("Email Address", email, "email@example.com")
        ContactField("Phone Number", phone, "(555) 000-0000")
        ContactMessageField(message)

        if (status == Success) {
            P(
                LabelSmStyle
                    .toModifier()
                    .color(AppColors.Secondary)
                    .margin(top = 12.px)
                    .toAttrs(),
            ) {
                Text("Thanks - we'll be in touch soon.")
            }
        }

        if (status == Error) {
            P(
                InputErrorStyle
                    .toModifier()
                    .textAlign(TextAlign.Center)
                    .margin(top = 12.px)
                    .toAttrs(),
            ) {
                Text("Please fill out every field with a valid email, phone number, and message.")
            }
        }
    }

    Button(
        {
            if (status == Submitting) return@Button

            coroutineScope.launch {
                val first = firstName.value.trim()
                val last = lastName.value.trim()
                val mail = email.value.trim()
                val tel = phone.value.trim()
                val body = message.value.trim()

                if (
                    first.isBlank() ||
                    last.isBlank() ||
                    mail.isBlank() ||
                    tel.isBlank() ||
                    body.isBlank() ||
                    !isValidEmail(mail) ||
                    !isValidPhone(tel)
                ) {
                    status = Error
                    return@launch
                }

                status = Submitting

                try {
                    postContact(first, last, mail, tel, body)
                    firstName.value = ""
                    lastName.value = ""
                    email.value = ""
                    phone.value = ""
                    message.value = ""
                    status = Success
                } catch (_: Exception) {
                    status = Error
                }
            }
        },
        PrimaryButtonStyle
            .toModifier()
            .minWidth(20.percent)
            .margin(top = AppSpacing.Gutter)
            .textAlign(TextAlign.Center)
            .justifyContent(JustifyContent.Center),
    ) {
        Text(
            when (status) {
                Idle -> "Send Message"
                Submitting -> "Sending..."
                Success -> "Sent!"
                Error -> "Please Check Your Details"
            },
        )
    }
}

@Composable
private fun ContactField(
    label: String,
    value: MutableState<String>,
    placeholderText: String,
) {
    Column(Modifier.fillMaxWidth().margin(bottom = AppSpacing s 2)) {
        Label(
            attrs =
                InputLabelStyle
                    .toModifier()
                    .toAttrs(),
        ) {
            Text(label)
        }
        TextInput(
            value.value,
            InputStyle
                .toModifier()
                .toAttrs {
                    placeholder(placeholderText)
                    onInput { value.value = it.value }
                },
        )
    }
}

@Composable
private fun ContactMessageField(value: MutableState<String>) {
    Column(
        Modifier.gridColumn("span 2").fillMaxWidth().margin(bottom = AppSpacing s 2),
    ) {
        Label(
            attrs =
                InputLabelStyle
                    .toModifier()
                    .toAttrs(),
        ) {
            Text("Message")
        }
        TextArea(
            value.value,
            InputStyle
                .toModifier()
                .height(160.px)
                .borderRadius(AppRadius.Default)
                .toAttrs {
                    placeholder("Tell us a little about what you're looking for")
                    onInput { value.value = it.value }
                },
        )
    }
}
