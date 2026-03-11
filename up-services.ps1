$ErrorActionPreference = 'Stop'

$scriptDir = Split-Path -Parent $MyInvocation.MyCommand.Path
$composeFile = Join-Path $scriptDir 'docker-compose.yml'

if (-not (Test-Path -LiteralPath $composeFile -PathType Leaf)) {
    Write-Error "docker-compose.yml not found at root: $composeFile"
    exit 1
}

$envArgs = New-Object System.Collections.Generic.List[string]

Get-ChildItem -LiteralPath $scriptDir -Directory | ForEach-Object {
    $serviceName = $_.Name

    if ($serviceName -cnotlike '*service*') {
        return
    }

    $envFile = Join-Path $_.FullName '.env'
    if (Test-Path -LiteralPath $envFile -PathType Leaf) {
        $envArgs.Add('--env-file')
        $envArgs.Add($envFile)
    }
    else {
        Write-Warning "'$serviceName' matches '*service*' but has no .env file"
    }
}

if ($envArgs.Count -eq 0) {
    Write-Error "No .env files found in root-level directories containing 'service'"
    exit 1
}

$dockerArgs = @('compose') + $envArgs + @('-f', $composeFile, 'up', '-d')
& docker @dockerArgs
