# Security Policy

## Supported Versions

| Version | Supported          |
|---------|--------------------|
| latest  | ✅ Yes             |
| < latest | ❌ No             |

Only the latest release on the default branch receives security updates.

---

## Reporting a Vulnerability

> **⚠️ Do NOT open a public GitHub issue for security vulnerabilities.**

If you discover a security vulnerability in this project, please report it **privately**:

1. **GitHub Private Reporting (preferred)**
   Use [GitHub's private vulnerability reporting](https://docs.github.com/en/code-security/security-advisories/guidance-on-reporting-and-writing-information-about-vulnerabilities/privately-reporting-a-security-vulnerability)
   by navigating to the repository's **Security** tab → **Report a vulnerability**.

2. **Email**
   Send a detailed report to: **security@example.com**

### What to Include

- Description of the vulnerability
- Steps to reproduce (proof-of-concept if possible)
- Affected version(s) or commit SHA
- Impact assessment (what could an attacker do?)
- Any suggested fix or mitigation

### Response Timeline

| Step                        | Target        |
|-----------------------------|---------------|
| Acknowledgement of report   | 48 hours      |
| Initial triage & assessment | 5 business days |
| Fix & coordinated disclosure | 30–90 days    |

We follow [responsible disclosure](https://en.wikipedia.org/wiki/Responsible_disclosure) practices.
You will be credited in the advisory unless you request anonymity.

---

## Security Best Practices (for contributors)

This project enforces the security standards documented in
[`.github/skills/developer.md`](.github/skills/developer.md) (see the **Security / OWASP** section).

Key rules:

- **No secrets in code** — use environment variables or a secrets manager
- **Parameterised queries only** — never concatenate user input into SQL
- **Validate all input** — `@Valid` on every `@RequestBody`
- **No sensitive data in logs** — never log passwords, tokens, or PII
- **Dependency scanning** — keep dependencies up-to-date; review Dependabot alerts promptly
- **Least privilege** — each service account gets only the permissions it needs

---

## Dependencies

This project uses the following dependency management practices:

- **Dependabot / Renovate** — automated dependency update PRs (when configured)
- **OWASP Dependency-Check** — can be enabled via Maven plugin for CVE scanning:

  ```bash
  ./mvnw org.owasp:dependency-check-maven:check
  ```

---

## Scope

This security policy applies to the code in this repository. Third-party dependencies
are subject to their own security policies and licenses.
