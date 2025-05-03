export async function deriveKey(
    passphrase: string,
    salt: Uint8Array
  ): Promise<CryptoKey> {
    const pwUtf8 = new TextEncoder().encode(passphrase);
    const keyMaterial = await crypto.subtle.importKey(
      'raw', pwUtf8, 'PBKDF2', false, ['deriveKey']
    );
    return crypto.subtle.deriveKey(
      {
        name: 'PBKDF2',
        salt,
        iterations: 100000,
        hash: 'SHA-256'
      },
      keyMaterial,
      { name: 'AES-GCM', length: 256 },
      true,
      ['encrypt', 'decrypt']
    );
  }
  
  export async function encryptAESGCM(
    plainText: string,
    key: CryptoKey
  ): Promise<string> {
    const iv = crypto.getRandomValues(new Uint8Array(12)); // 96-bit nonce
    const ptUint8 = new TextEncoder().encode(plainText);
    const ctBuffer = await crypto.subtle.encrypt(
      { name: 'AES-GCM', iv },
      key,
      ptUint8
    );
    const ctArray = new Uint8Array(ctBuffer);
    const ivStr = btoa(String.fromCharCode(...iv));
    const ctStr = btoa(String.fromCharCode(...ctArray));
    return `${ivStr}:${ctStr}`;
  }
  