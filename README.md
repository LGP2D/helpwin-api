# helpwin-api
This is the API project for the Helpwin web application. It servers as a communication conductor between front end and database.

## How does your git model works?
```
May branch off from:
develop
Must merge back into:
develop
```

#### Always use develop branch to start branching off. Branching names convention must follow: `develop/name`

If you want to start developing, first create a new branch:

```
$ git checkout -b myfeature develop
Switched to a new branch "myfeature"
```

> Replace `myfeature` with your branch name, ex: feat/register

#### When finished developing the feature, ***merge back*** to develop.

> The --no-ff flag causes the merge to always create a new commit object, even if the merge could be performed with a fast-forward. This avoids losing information about the historical existence of a feature branch and groups together all commits that together added the feature.

```
/* We're going to switch to develop, the branch where we are doing changes */
$ git checkout develop
Switched to branch 'develop'

/* Now we tell 'myfeature' branch to merge with us (develop)
$ git merge --no-ff myfeature
Updating ea1b82a..05e9557
(Summary of changes)

/* Now we delete the myfeature branch, because it's already merged with develop */
$ git branch -d myfeature
Deleted branch myfeature (was 05e9557).

/* We push the changes */
$ git push origin develop

/* This will update github with saying the feature branch was deleted */
$ git push origin --delete myfeature

```

> Replace `myfeature` with your branch name, ex: feat/register
