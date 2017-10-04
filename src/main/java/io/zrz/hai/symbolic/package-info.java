
/**
 * HAI symbolic representation, a representation of HAI models, statements, and
 * expressions which is encoded as a bundle and loaded into the engine at
 * runtime.
 *
 * It consists of four parts: TypeInfo, StorageInfo, CodeInfo, and SourceInfo.
 *
 * TypeInfo contains the model, including method signatures. StorageInfo
 * contains mapping IDs from the code to the datastore. CodeInfo contains
 * "bytecode" representation of statements and expressions. SourceInfo maps type
 * and code entries to the original source.
 *
 * Full semantic representation of the source is retained. HAI isn't a typical
 * "compiler" and "runtime", as it builds execution paths on demand based on the
 * input. In a sense, it performs the actual compilation at runtime when the
 * query or mutation is received.
 *
 */

package io.zrz.hai.symbolic;
