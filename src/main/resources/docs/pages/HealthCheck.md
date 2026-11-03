# Health Check

## Overview
Reports basic extension health — current JVM heap usage — so an operator can confirm the extension
process is running normally.

## Request Details
- **Area**: Setup
- **Type**: CHANGE_SYSTEM
- **External API**: N/A — self-contained, no external call

## Input Parameters
*(none)*

## Output Parameters
| Parameter | Type | Description |
|---|---|---|
| Is Healthy | Boolean | Always `true` — there is no external dependency that could make the extension unhealthy. |
| Heap Used MB | Number | Current JVM heap usage, in megabytes. |
| Details | Text | A short status string (`OK`). |

## Example
**Input**: *(none)*
**Output**: Is Healthy=`true`, Heap Used MB=`128`, Details=`OK`

## Error Handling
- None expected — this request only reads local JVM memory statistics.
