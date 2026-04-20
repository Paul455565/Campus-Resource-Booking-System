# Kanban Board Explanation

## What is a Kanban Board?
A Kanban board is a visual project management tool that maps tasks across workflow stages using columns (e.g., To Do → In Progress → Done), limits work-in-progress (WIP) to prevent overload, and provides real-time status visibility for teams.

## How Our Board Works
- **Visualizes Workflow**: Columns represent progress: To Do (backlog), In Progress (active dev), Review (QA/PR), Testing (custom), Blocked (dependencies), Done (shipped).
- **WIP Limits**: Set to 3 tasks/column to focus effort, avoid bottlenecks (e.g., no more than 3 devs on In Progress).
- **Agile Support**: Enables continuous delivery (pull issues as capacity allows), adaptability (move blocked tasks), collaboration (@team mentions, labels: `feature`, `bug`).

Example: A "Resource Booking API" issue moves from To Do → In Progress (dev starts) → Review (PR) → Testing (QA) → Done (deployed), with automation triggering moves.
