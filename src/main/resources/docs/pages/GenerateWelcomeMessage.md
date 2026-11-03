# Generate Welcome Message

## Overview
Composes a personalized welcome/greeting message for a named recipient. This is the only request
most workflows need — it does everything locally and calls no external system.

## Request Details
- **Area**: Welcome
- **Type**: QUERY_SYSTEM
- **External API**: N/A — self-contained, no external call

## Input Parameters
| Parameter | Type | Required | Description |
|---|---|---|---|
| Recipient Name | Text | Yes | The name of the person to welcome. Used to fill in the `{{name}}` placeholder of whichever template is used. |
| Custom Message | Paragraph | No | An optional template that overrides the default greeting. Include `{{name}}` to have the recipient's name inserted; if supplied without the placeholder, it is returned exactly as given. When omitted, a default greeting template is used. |

## Output Parameters
| Parameter | Type | Description |
|---|---|---|
| Welcome Message | Paragraph | The generated welcome/greeting message text. |

## Example
**Input**: Recipient Name=`Jane Doe`, Custom Message=*(omitted)*
**Output**: Welcome Message=`Hello Jane Doe, welcome! We're delighted to have you here.`

**Input**: Recipient Name=`Sam`, Custom Message=`Hi {{name}}, great to have you on the team!`
**Output**: Welcome Message=`Hi Sam, great to have you on the team!`

## Error Handling
- **Recipient Name missing or blank** — returns an input-error response asking for a value, e.g.
  `Recipient Name is required. Please provide a value for Recipient Name, e.g. "Jane Doe".`
