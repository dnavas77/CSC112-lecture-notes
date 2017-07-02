package search;

import java.io.*;
import java.util.*;

/**
 * This class encapsulates an occurrence of a keyword in a document. It stores the
 * document name, and the frequency of occurrence in that document. Occurrences are
 * associated with keywords in an index hash table.
 * 
 * @author Sesh Venugopal
 * 
 */
class Occurrence {
	/**
	 * Document in which a keyword occurs.
	 */
	String document;

	/**
	 * The frequency (number of times) the keyword occurs in the above document.
	 */
	int frequency;
	
	/**
	 * Initializes this occurrence with the given document,frequency pair.
	 * 
	 * @param doc Document name
	 * @param freq Frequency
	 */
	public Occurrence(String doc, int freq) {
		document = doc;
		frequency = freq;
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return "(" + document + "," + frequency + ")";
	}
}

/**
 * This class builds an index of keywords. Each keyword maps to a set of documents in
 * which it occurs, with frequency of occurrence in each document. Once the index is built,
 * the documents can searched on for keywords.
 *
 */
public class LittleSearchEngine {
	
	/**
	 * This is a hash table of all keywords. The key is the actual keyword, and the associated value is
	 * an array list of all occurrences of the keyword in documents. The array list is maintained in descending
	 * order of occurrence frequencies.
	 */
	HashMap<String, ArrayList<Occurrence>> keywordsIndex;
	
	/**
	 * The hash table of all noise words - mapping is from word to itself.
	 */
	HashMap<String,String> noiseWords;
	
	/**
	 * Creates the keyWordsIndex and noiseWords hash tables.
	 */
	public LittleSearchEngine() {
		keywordsIndex = new HashMap<String,ArrayList<Occurrence>>(1000,2.0f);
		noiseWords = new HashMap<String,String>(100,2.0f);
	}
	
	/**
	 * This method indexes all keywords found in all the input documents. When this
	 * method is done, the keywordsIndex hash table will be filled with all keywords,
	 * each of which is associated with an array list of Occurrence objects, arranged
	 * in decreasing frequencies of occurrence.
	 * 
	 * @param docsFile Name of file that has a list of all the document file names, one name per line
	 * @param noiseWordsFile Name of file that has a list of noise words, one noise word per line
	 * @throws FileNotFoundException If there is a problem locating any of the input files on disk
	 */
	public void makeIndex(String docsFile, String noiseWordsFile) 
	throws FileNotFoundException {
		// load noise words to hash table
		Scanner sc = new Scanner(new File(noiseWordsFile));
		while (sc.hasNext()) {
			String word = sc.next();
			noiseWords.put(word,word);
		}
		
		// index all keywords
		sc = new Scanner(new File(docsFile));
		while (sc.hasNext()) {
			String docFile = sc.next();
			HashMap<String,Occurrence> kws = loadKeyWords(docFile);
			mergeKeyWords(kws);
		}
	}

	/**
	 * Scans a document, and loads all keywords found into a hash table of keyword occurrences
	 * in the document. Uses the getKeyWord method to separate keywords from other words.
	 * 
	 * @param docFile Name of the document file to be scanned and loaded
	 * @return Hash table of keywords in the given document, each associated with an Occurrence object
	 * @throws FileNotFoundException If the document file is not found on disk
	 */
	public HashMap<String, Occurrence> loadKeyWords(String docFile)
	throws FileNotFoundException {
		// COMPLETE THIS METHOD
        HashMap<String, Occurrence> docKeywords = new HashMap<String, Occurrence>(500, 2.0f);
        Scanner sc1;

        try
        {
            sc1 = new Scanner(new File(docFile));

            while (sc1.hasNextLine())
            {
                Scanner cs2 = new Scanner(sc1.nextLine());
                while (cs2.hasNext())
                {
                    String wrd = getKeyWord(cs2.next());

                    if (wrd == null) continue;

                    if (docKeywords.containsKey(wrd))
                    {
                       docKeywords.get(wrd).frequency = docKeywords.get(wrd).frequency + 1;
                    }
                    else
                    {
                       docKeywords.put(wrd, new Occurrence(docFile, 1));
                    }
//                    System.out.println(wrd);
                }
            }
        }
        catch (FileNotFoundException e)
        {
            e.printStackTrace();
        }

		return docKeywords;
	}
	
	/**
	 * Merges the keywords for a single document into the master keywordsIndex
	 * hash table. For each keyword, its Occurrence in the current document
	 * must be inserted in the correct place (according to descending order of
	 * frequency) in the same keyword's Occurrence list in the master hash table. 
	 * This is done by calling the insertLastOccurrence method.
	 * 
	 * @param kws Keywords hash table for a document
	 */
	public void mergeKeyWords(HashMap<String, Occurrence> kws) {
		// COMPLETE THIS METHOD

		// Iterate through each elem in kws HashMap using Iterator
        for (String key: kws.keySet())
        {
            Occurrence kwsOcc = kws.get(key);

            /* Check if key is in master HashMap. */
            if (keywordsIndex.containsKey(key))
            {
                // Check if occurrence exist for document in 'kws'.
                ArrayList<Occurrence> occArrayForKey =  keywordsIndex.get(key);

                // Iterate through all Occurrences in 'occArrayForKey' ArrayList.
                boolean found = false;
                for (int i = 0; i < occArrayForKey.size(); i++)
                {
                    /* Check if a document in master hash ArrayList equals document name in 'kws'.
                       If equal, increase frequency index only. */
                    if (occArrayForKey.get(i).document.equals(kwsOcc.document))
                    {
                        keywordsIndex.get(key).get(i).frequency =
                                keywordsIndex.get(key).get(i).frequency + 1;
                        found = true;
                        break;
                    }
                }
                /* If 'kws' document was not found in key ArrayList,
                   Add new occurrence in ArrayList for key. */
                if (!found)
                {
                    keywordsIndex.get(key).add(kwsOcc); // Add last Occurrence
                    insertLastOccurrence(keywordsIndex.get(key)); // Sort in descending order
                }
            }
            else // If key is not in master hash map, add new key with new ArrayList<Occurrence>
            {
                ArrayList<Occurrence> ao = new ArrayList<Occurrence>();
                ao.add(kwsOcc);
                keywordsIndex.put(key, ao);
            }
        }
	}// end
	
	/**
	 * Given a word, returns it as a keyword if it passes the keyword test,
	 * otherwise returns null. A keyword is any word that, after being stripped of any
	 * TRAILING punctuation, consists only of alphabetic letters, and is not
	 * a noise word. All words are treated in a case-INsensitive manner.
	 * 
	 * Punctuation characters are the following: '.', ',', '?', ':', ';' and '!'
	 * 
	 * @param word Candidate word
	 * @return Keyword (word without trailing punctuation, LOWER CASE)
	 */
	public String getKeyWord(String word) {
		// COMPLETE THIS METHOD
        int i;
        String keyword = null;
        word = word.trim();

        // Remove trailing punctuation.
        for (i = word.length()-1; i >= 0; i--)
        {
//            if (word.charAt(i) == ',' || word.charAt(i) == '.' || word.charAt(i) == ';' ||
//                word.charAt(i) == ':' || word.charAt(i) == '?' || word.charAt(i) == '!' ||
//                    word.charAt(i) == '\'' || word.charAt(i) == '\"')
            if (!Character.isLetter(word.charAt(i)))
            {
                continue;
            }

            keyword = word.substring(0, i+1);
            break;
        }

        // If no word was found above, return null.
        if (keyword == null) return null;

        // If one character is non-alphabetic return null
        for (i = 0; i < keyword.length(); i++)
        {
            if (!Character.isLetter(keyword.charAt(i))) return null;
        }

        // Convert to lowercase and trim just in case
        keyword = keyword.toLowerCase().trim();

        // Check if it's not noise word.
        if (this.noiseWords.containsKey(keyword)) return null;

		return keyword;
	}
	
	/**
	 * Inserts the last occurrence in the parameter list in the correct position in the
	 * same list, based on ordering occurrences on descending frequencies. The elements
	 * 0..n-2 in the list are already in the correct order. Insertion of the last element
	 * (the one at index n-1) is done by first finding the correct spot using binary search, 
	 * then inserting at that spot.
	 * 
	 * @param occs List of Occurrences
	 * @return Sequence of mid point indexes in the input list checked by the binary search process,
	 *         null if the size of the input list is 1. This returned array list is only used to test
	 *         your code - it is not used elsewhere in the program.
	 */
	public ArrayList<Integer> insertLastOccurrence(ArrayList<Occurrence> occs) {
		// COMPLETE THIS METHOD

        if (occs.size() <= 1) return null; // If only 1 or 0 elem in ArrayList, do nothing.

        int lastFreq = occs.get(occs.size()-1).frequency; // Last elem's frequency
        Occurrence lastInOccs = occs.get(occs.size() -1);

        int lo = 0;
        int hi = occs.size() - 1;
        int mid;
        ArrayList<Integer> midIndexesArray = new ArrayList<Integer>();

        while(lo <= hi)
        {
            mid = (lo + hi) / 2;
            midIndexesArray.add(mid);

            if (lastFreq == occs.get(mid).frequency) break;

            if (lastFreq > occs.get(mid).frequency)
                hi = mid - 1;
            else if (lastFreq < occs.get(mid).frequency)
                lo = mid + 1;
        }

        if (midIndexesArray.get(midIndexesArray.size()-1) == 0 &&
            lastInOccs.frequency < occs.get(0).frequency)
        {
            occs.add(1, lastInOccs);
            occs.remove(occs.size()-1);
            return midIndexesArray;
        }

        occs.add(midIndexesArray.get(midIndexesArray.size()-1), lastInOccs);
        occs.remove(occs.size()-1);

        return midIndexesArray;
	}

	/**
	 * Search result for "kw1 or kw2". A document is in the result set if kw1 or kw2 occurs in that
	 * document. Result set is arranged in descending order of occurrence frequencies. (Note that a
	 * matching document will only appear once in the result.) Ties in frequency values are broken
	 * in favor of the first keyword. (That is, if kw1 is in doc1 with frequency f1, and kw2 is in doc2
	 * also with the same frequency f1, then doc1 will appear before doc2 in the result. 
	 * The result set is limited to 5 entries. If there are no matching documents, the result is null.
	 * 
	 * @param kw1 First keyword
	 * @param kw2 Second keyword
	 * @return List of NAMES of documents in which either kw1 or kw2 occurs, arranged in descending order of
	 *         frequencies. The result size is limited to 5 documents. If there are no matching documents,
	 *         the result is null.
	 */
	public ArrayList<String> top5search(String kw1, String kw2) {
		// COMPLETE THIS METHOD
        String keyw1 = kw1.toLowerCase().trim();
        String keyw2 = kw2.toLowerCase().trim();
        int i, j, docListLimit = 6;

        ArrayList<Occurrence> kw1OccList = new ArrayList<Occurrence>();
        ArrayList<Occurrence> kw2OccList = new ArrayList<Occurrence>();

        ArrayList<String> docList = new ArrayList<String>();

        if (keywordsIndex.get(keyw1) != null) kw1OccList = keywordsIndex.get(keyw1);
        if (keywordsIndex.get(keyw2) != null) kw2OccList = keywordsIndex.get(keyw2);

        // Find
        for (i = 0; i < kw1OccList.size(); i++)
        {
            if (docList.size() < docListLimit)
            {
                int kw1Freq = kw1OccList.get(i).frequency;
                String kw1DocName = kw1OccList.get(i).document;

                for (j = 0; j < kw2OccList.size(); j++)
                {
                    int kw2Freq = kw2OccList.get(j).frequency;
                    String kw2DocName = kw2OccList.get(j).document;

                    if (kw2Freq <= kw1Freq)
                    {
                        if (!docList.contains(kw1DocName) && docList.size() < docListLimit)
                            docList.add(kw1DocName);
                    }
                    else if (kw2Freq > kw1Freq)
                    {
                        if (!docList.contains(kw2DocName) && docList.size() < docListLimit)
                            docList.add(kw2DocName);
                    }
                }
            }
        }

        if (docList.isEmpty()) return null;

		return docList;
	}//end
}