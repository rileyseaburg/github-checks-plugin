# Release Automation

This directory contains GitHub Actions workflows for automated releases and continuous deployment.

## Workflows

### 1. Release Workflow (`release.yml`)

**Triggers:**
- Push to `master` or `main` branch (automatic on PR merge)
- Manual dispatch with optional version override

**Features:**
- Automatic version increment (patch level by default)
- Builds and tests the plugin
- Creates GitHub releases with release notes
- Attaches the HPI file to releases
- Handles version management in `pom.xml`
- Optional publishing to Jenkins Update Center (if Maven credentials are configured)

**Usage:**
- **Automatic**: Merging a PR to `master` will automatically create a new release
- **Manual**: Use "Run workflow" in GitHub Actions with optional version number
- **Skip**: Add `[skip release]` to commit message to prevent automatic release

### 2. Release Drafter (`release-drafter.yml`)

Creates draft releases with categorized changelog based on PR labels.

### 3. Continuous Deployment (`cd.yaml`)

Jenkins infrastructure deployment workflow for official plugin releases.

### 4. CI (`maven.yml`)

Runs tests and builds on all PRs and pushes.

## Configuration

### Version Management

The release workflow automatically increments the patch version. To control versioning:

- Add label `major` to PR for major version bump
- Add label `minor` to PR for minor version bump  
- Add label `patch` to PR for patch version bump (default)

### Secrets Required

For full functionality, configure these repository secrets:

- `MAVEN_USERNAME` - Jenkins repository username (optional, for Jenkins Update Center publishing)
- `MAVEN_TOKEN` - Jenkins repository token (optional, for Jenkins Update Center publishing)

### Release Notes

Release notes are automatically generated from:
- Git commit messages since last release
- PR titles and labels (via Release Drafter)

## Manual Release Process

If you need to create a release manually:

1. Go to Actions → Release workflow
2. Click "Run workflow"  
3. Optionally specify a version number
4. The workflow will build, test, and create the release

## Files Generated

Each release includes:
- `github-checks.hpi` - The Jenkins plugin file
- Release notes with installation instructions
- Git tag in format `vX.Y.Z`