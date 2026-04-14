# Release flow

Short guide for cutting a release.

## Branching model (GitHub Flow)

One long-lived branch — `master` (or `main`, whichever the repo is on).
Feature work lives on `feature/**`, `task/**`, `fix/**` branches that are
merged back via pull requests. `develop` is kept for staging pre-release
work before a tag.

- `master` — always deployable, tagged on every release
- `develop` — integration branch for the next release
- `feature/*`, `fix/*`, `task/*` — short-lived, merged into `develop` (or
  straight to `master` for hotfixes) through a PR

Both `master` and `develop` are protected by the [Build workflow](.github/workflows/build.yml)
— every push and pull request runs `./gradlew build shadowJar` and publishes
the JAR as an artifact, plus a JUnit test report.

## Cutting a release

1. **Bump the version.** Edit `build.gradle`:
   ```gradle
   version '1.19.0'
   ```
   Commit with a message like `chore(release): 1.19.0`. The [Release workflow](.github/workflows/release.yml)
   compares the tag against this field and aborts if they disagree.

2. **Merge `develop` → `master`** via PR (or push directly if you are the
   sole maintainer).

3. **Create a release on GitHub.** Either through the web UI or the CLI:
   ```bash
   gh release create 1.19.0 \
       --title "AbstractMenus 1.19.0" \
       --notes-file CHANGELOG.md
   ```
   Tag format: either `1.19.0` or `v1.19.0` — the workflow strips the
   leading `v` before comparing.

4. **Release workflow fires automatically** (`on: release: types:
   [published]`), rebuilds the shaded JAR from the tagged commit, verifies
   the version matches, and uploads `AbstractMenus-1.19.0.jar` as a release
   asset.

5. Done. The release page now has the JAR; users pull it straight from
   there.

## Re-attaching a JAR to an existing release

If the build failed or the asset was deleted, trigger the Release workflow
manually:

- GitHub UI → Actions → Release → Run workflow → supply the existing tag
  (e.g. `1.18.0`).
- Or via CLI: `gh workflow run release.yml -f tag=1.18.0`.

The workflow will rebuild from that tag's commit and re-upload. If an
asset with the same name already exists, `softprops/action-gh-release`
overwrites it.

## Hotfix flow

For a patch release that doesn't touch `develop`:

1. Branch from `master`: `git checkout -b fix/bad-null-deref master`
2. Fix, PR, merge.
3. Bump `version` on `master`: `1.19.0` → `1.19.1`. Commit.
4. Cut the GitHub release as in the main flow. The workflow picks up the
   new version automatically.
5. Cherry-pick the fix commit back to `develop` so it isn't lost.

## Pre-releases

GitHub supports a "Set as a pre-release" checkbox on the release form.
That marks the tag as a pre-release (e.g. `1.19.0-rc1`, `1.19.0-beta.2`)
— the Release workflow still builds and attaches the JAR, users just see
the pre-release badge on the release page.

For a pre-release version bump, use Gradle-compatible syntax:

```gradle
version '1.19.0-rc1'
```

The workflow's version-match check strips only a leading `v`, so
`1.19.0-rc1` and `v1.19.0-rc1` both work.
