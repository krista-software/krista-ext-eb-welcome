# Test Connection

## Overview
Verifies the extension is loaded and operating normally. Since EB Welcome has no external system or
credentials, this always succeeds once the extension is installed.

## Request Details
- **Area**: Setup
- **Type**: QUERY_SYSTEM
- **External API**: N/A — self-contained, no external call

## Input Parameters
*(none)*

## Output Parameters
| Parameter | Type | Description |
|---|---|---|
| Success | Boolean | Always `true` — there is no external connectivity to fail. |
| Message | Text | A confirmation message. |

## Example
**Input**: *(none)*
**Output**: Success=`true`, Message=`EB Welcome is operating normally. No external system is configured — this extension generates welcome messages locally.`

## Error Handling
- None expected — this request has no external dependency that can fail.
