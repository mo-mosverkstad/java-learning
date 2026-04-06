package se.ebikerepair.util;

/**
 * Parses and normalizes Swedish telephone numbers to E.164 format.
 * Supports formats: +46..., 0046..., 0..., and numbers with spaces/dashes/parentheses.
 */
public class TelephoneNumber {
    private String cc;
    private String ac;
    private String sn;

    /**
     * Parses a telephone number string into country code, area code, and subscriber number.
     *
     * @param telephoneNumber the telephone number in any supported Swedish format
     * @throws IllegalArgumentException if the telephone number is null, empty, or has an invalid format
     */
    public TelephoneNumber(String telephoneNumber) {
        if (telephoneNumber == null || telephoneNumber.trim().isEmpty()) {
            throw new IllegalArgumentException("Telephone number format problem");
        }

        String digits = telephoneNumber.replaceAll("[\\s\\-\\(\\)]", "");

        if (!digits.matches("\\+?[0-9]+")) {
            throw new IllegalArgumentException("Telephone number format problem");
        }

        String normalized;

        if (digits.startsWith("+")) {
            normalized = digits.substring(1);
        } else if (digits.startsWith("00")) {
            normalized = digits.substring(2);
        } else if (digits.startsWith("0")) {
            normalized = "46" + digits.substring(1);
        } else {
            normalized = "46" + digits;
        }

        if (normalized.length() < 7) {
            throw new IllegalArgumentException("Telephone number format problem");
        }

        cc = extractCountryCode(normalized);
        String remaining = normalized.substring(cc.length());

        if (remaining.isEmpty() || (remaining.length() < 8 || remaining.length() > 9)) {
            throw new IllegalArgumentException("Telephone number format problem");
        }

        ac = extractAreaCode(remaining);
        sn = remaining.substring(ac.length());

        if (sn.isEmpty()) {
            throw new IllegalArgumentException("Telephone number format problem");
        }
    }

    private String extractCountryCode(String digits) {
        String[][] ccTable = {
            {"1"},
            {"20", "27"},
            {"30", "31", "32", "33", "34", "36", "39"},
            {"40", "41", "43", "44", "45", "46", "47", "48", "49"},
            {"51", "52", "53", "54", "55", "56", "57", "58"},
            {"60", "61", "62", "63", "64", "65", "66"},
            {"7"},
            {"81", "82", "84", "86"},
            {"90", "91", "92", "93", "94", "95", "98"}
        };

        for (String[] group : ccTable) {
            for (String code : group) {
                if (digits.startsWith(code)) {
                    return code;
                }
            }
        }

        if (digits.length() >= 3) {
            return digits.substring(0, 3);
        }
        return digits;
    }

    private String extractAreaCode(String digits) {
        if (digits.length() >= 2 && isMobilePrefix(digits.substring(0, 2))) {
            return digits.substring(0, 2);
        }
        if (digits.length() >= 1 && isSingleDigitAreaCode(digits.substring(0, 1))) {
            return digits.substring(0, 1);
        }
        if (digits.length() >= 3) {
            return digits.substring(0, 3);
        }
        if (digits.length() >= 2) {
            return digits.substring(0, 2);
        }
        return digits;
    }

    private boolean isMobilePrefix(String prefix) {
        String[] mobilePrefixes = {"70", "72", "73", "76", "79"};
        for (String mp : mobilePrefixes) {
            if (prefix.equals(mp)) {
                return true;
            }
        }
        return false;
    }

    private boolean isSingleDigitAreaCode(String prefix) {
        String[] singleDigitACs = {"8"};
        for (String ac : singleDigitACs) {
            if (prefix.equals(ac)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Returns the country code (e.g. "46" for Sweden).
     *
     * @return the country code
     */
    public String getCc() {
        return cc;
    }

    /**
     * Returns the area code (e.g. "70" for mobile).
     *
     * @return the area code
     */
    public String getAc() {
        return ac;
    }

    /**
     * Returns the subscriber number.
     *
     * @return the subscriber number
     */
    public String getSn() {
        return sn;
    }

    /**
     * Returns the telephone number in E.164 format (e.g. "+46701234567").
     *
     * @return the E.164 formatted telephone number
     */
    public String toE164() {
        return "+" + cc + ac + sn;
    }

    @Override
    public String toString() {
        return "TelephoneNumber{cc='" + cc + "', ac='" + ac + "', sn='" + sn + "', E.164='" + toE164() + "'}";
    }
}

