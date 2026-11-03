# EB Welcome — Implementation Plan

## Summary
A minimal, self-contained Krista extension that generates a personalized welcome/greeting
message. It calls no external API and requires no credentials — per the user's explicit
answers, this build is a text generator only ("Generate the message text only — no external
delivery system"), not an integration that sends email/Slack/Teams messages.

- **Extension title:** EB Welcome
- **Extension name (slug):** `eb-welcome`
- **Version:** 1.0.0
- **Package:** `app.krista.extensions.ebplayground.ebtesting.ebwelcome`
- **Ecosystem:** EB Playground
- **Domain:** EB Testing

## Area: Welcome Messages

| Request | Inputs | Output | External endpoint | Reused or new |
|---|---|---|---|---|
| Generate Welcome Message | Recipient Name (Text, required); Custom Message (Paragraph, optional) | Welcome Message (scalar Paragraph text) | N/A — self-contained, no external call | **New** |

**Behavior:** if `Custom Message` is not supplied, the extension composes a default greeting
template using `Recipient Name`; if it is supplied, that text is used as the template (with the
recipient's name substituted in) instead of the built-in default. No value is persisted and no
other system is contacted.

**Tests planned:**
- Happy path: only `Recipient Name` given → default-template message containing that name.
- `Custom Message` given → that template is used (with name substitution) instead of the default.
- Validation: missing/blank `Recipient Name` is rejected before generating a message.

## Entities
None. The output is a small, ad-hoc scalar result (a generated message string), which per the
framework's Entity-vs-scalar rule does not warrant an `@Entity` — only domain objects with a
stable multi-field shape do.

## Authentication
**Pattern: NONE.** The user's answer to the authentication question was `"none"`, and this is
consistent with the rest of the material: the extension calls no external system (confirmed by
the "no external delivery system" answer), so there is nothing to authenticate against. No
secured connection fields and no Setup/Authentication tab requirement. (A `SetupArea` with a
basic Test Connection / Health Check is still included per the scaffolding's standard
convention, but it has no credentials to validate.)

## Capabilities
| Capability | Enabled? |
|---|---|
| Entity search | No |
| Webhooks / WAIT_FOR_EVENT | No |
| MCP (`tool = true`) | No |
| Solutions tab | No |
| File handling | No |

None were indicated by the requirement or answers, so none are built.

## Domain reuse
`domain-catalog.json` reports `"found": false` for EB Playground / EB Testing — this
ecosystem/domain is brand new, so there is nothing published to compare against or reuse.
Every area, request, and entity in this plan is therefore **new** by default; no
domain-reuse clarification was needed.

## Assumption flagged for review
The free-text answer to "what information should shape the welcome message" was `"yes"`, which
doesn't specify concrete fields. This plan assumes the simplest reasonable shape for a welcome
message generator: a required recipient name plus an optional custom message/template that
overrides the default greeting. If something more specific was intended (e.g. company name,
role, language, a fixed corporate template), say so now and the plan will be revised before
code is generated.

## Placeholder IDs — must replace before deploy
- `@Domain` block (`domainId`, `ecosystemId`, `ecosystemName`) for **EB Playground / EB Testing**
  is **newly minted (placeholder UUIDs)** because this ecosystem/domain has never been published
  to the real Krista catalog (`domain-catalog.json` → `found: false`). These placeholders compile
  fine but **will not bind** to a real domain/ecosystem until EB Playground/EB Testing is
  registered in Krista and the real IDs are substituted.
- `ecosystemVersion` is a stable placeholder UUID (this one is expected to vary per file/release
  and does not need a "real" registered value).

No other placeholders are needed — there is no external base URL, auth secret, or vendor API
credential for this extension.
